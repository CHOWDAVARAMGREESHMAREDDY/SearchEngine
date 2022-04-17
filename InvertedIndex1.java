import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InvertedIndex1 {

	public TreeMap<String, TreeMap<Integer, LinkedList<Integer>>> index = new TreeMap<>();

	public Map<Integer, String> sources = new HashMap<>();

	private List<List<String>> listOfLists = new ArrayList<>();

	private Map<Integer, List<String>> mapOfEachDocData = new TreeMap<>();

	private Map<String, Integer> queryTermFrequency = new TreeMap<>();

	public Map<String, Double> queryVector = new TreeMap<>();

	public int termFrequency(String word, Integer docID) {
		TreeMap<Integer, LinkedList<Integer>> temp = index.get(word);
		if (temp != null) {
			LinkedList<Integer> temp1 = temp.get(docID);
			if (temp1 != null) {
				return temp1.size();
			}
		}
		return 0;
	}

	public int docFrequency(String word) {
		if (index.get(word) != null) {
			return index.get(word).size();
		}
		return 0;
	}

	public double invertedDocFrequency(String word) {
		int docFreq = docFrequency(word);
		if (docFreq != 0) {
			double size = sources.size();
			double tt = size / docFreq;
			return Math.log10(tt);
		}
		return 0;
	}

	public double tfIDF(String word, Integer docID) {
		return invertedDocFrequency(word) * termFrequency(word, docID);
	}

	public Map<Integer, List<Double>> docVectors() {
		Map<Integer, List<Double>> docVectors = new TreeMap<>();
		for (Integer docID : sources.keySet()) {
			List<Double> docVector = new ArrayList<>();
			for (String word : index.keySet()) {
				docVector.add(tfIDF(word, docID));
			}
			docVectors.put(docID, docVector);
		}

		return docVectors;
	}

	public void queryVector() {
		Map<String, Double> queryVector = new TreeMap<>();
		for (String queryTerm : queryTermFrequency.keySet()) {
			queryVector.put(queryTerm, queryTermFrequency.get(queryTerm) * invertedDocFrequency(queryTerm));
		}
		this.queryVector = queryVector;
	}

	public void caluclateSimilarity() {
		for (Integer docID : sources.keySet()) {
			System.out.println(
					"Cosine Similarity between " + "docID:" + docID + " " + cosineSimilarity1(queryVector, docID));
		}
		System.out.println();
	}

	public Double cosineSimilarity(List<Double> queryVector, List<Double> docVector) {
		Double dotProduct = 0.0;
		Double normA = 0.0;
		Double normB = 0.0;
		for (int i = 0; i < queryVector.size(); i++) {
			dotProduct += queryVector.get(i) * docVector.get(i);
			normA += Math.pow(queryVector.get(i), 2);
			normB += Math.pow(docVector.get(i), 2);
		}
		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}

	public Double cosineSimilarity1(Map<String, Double> queryVector, Integer docID) {
		Double dotProduct = 0.0;
		Double normA = 0.0;
		Double normB = 0.0;
		for (String queryWord : queryVector.keySet()) {
			dotProduct = tfIDF(queryWord, docID) * (queryVector.get(queryWord));
			normA += Math.pow(queryVector.get(queryWord), 2);
		}
		normB = getNormalizationValue(docID);
		if (dotProduct == 0 || normA == 0 || normB == 0) {
			return 0.0;
		}
		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}

	/*
	 * public static void buildIndex(String[] files) {
	 * 
	 * Integer docID = 0; for (String filePath : files) { try (BufferedReader file =
	 * new BufferedReader(new FileReader(filePath))) { sources.put(docID, filePath);
	 * byte[] buffer; buffer = Files.readAllBytes(Paths.get(filePath)); String whole
	 * = new String(buffer, StandardCharsets.UTF_8); String ln; int k = 0; while
	 * ((ln = file.readLine()) != null) { String[] words = ln.split("\\W+"); for
	 * (String word : words) { LinkedList<Integer> pos = new LinkedList<>(); word =
	 * word.toLowerCase(); if (!index.containsKey(word)) { TreeMap<Integer,
	 * LinkedList<Integer>> postings = new TreeMap<>(); pos.add(k);
	 * postings.put(docID, pos); index.put(word, postings); } else { if
	 * (index.get(word).containsKey(docID)) { index.get(word).get(docID).add(k); }
	 * else { pos.add(k); index.get(word).put(docID, pos); } }
	 * 
	 * k++; } } } catch (IOException e) { System.out.println("File " + filePath +
	 * " not found. Skip it"); } docID++; } System.out.println(); }
	 */

	public void buildIndex(String[] files) {

		Integer docID = 0;
		for (String filePath : files) {
			try {
				sources.put(docID, filePath);
				byte[] buffer;
				buffer = Files.readAllBytes(Paths.get(filePath));
				String entireDocText = new String(buffer);
				PreProcessor preProcessor = new PreProcessor();
				Stemmer stemmer = new Stemmer();
				entireDocText = preProcessor.removeHTMLTags(entireDocText);
				entireDocText = preProcessor.removeStopWords(entireDocText);
				entireDocText = stemmer.stemmerMain(entireDocText, 0);
				String[] textArray = entireDocText.split("\\s+");
				int k = 0;
				listOfLists.add(new ArrayList<>(Arrays.asList(textArray)));
				mapOfEachDocData.put(docID, new ArrayList<>(Arrays.asList(textArray)));
				for (String word : textArray) {
					if (!word.isBlank() || word.length() != 0 || !word.isEmpty()) {
						LinkedList<Integer> pos = new LinkedList<>();
						word = word.trim().toLowerCase();
						if (!index.containsKey(word)) {
							TreeMap<Integer, LinkedList<Integer>> postings = new TreeMap<>();
							pos.add(k);
							postings.put(docID, pos);
							index.put(word, postings);
						} else {
							if (index.get(word).containsKey(docID)) {
								index.get(word).get(docID).add(k);
							} else {
								pos.add(k);
								index.get(word).put(docID, pos);
							}
						}
						k++;
					}
				}
			} catch (IOException e) {
				System.out.println("Exception: " + e);
			}
			docID++;
		}
	}

	public void temp(String[] files) {
		try {
			for (int i = 0; i < listOfLists.size(); i++) {
				List<String> words = listOfLists.get(i);
				double sum = 0;
				for (int j = 0; j < words.size(); j++) {
					double result = InvertedIndex.tfIDF(words.get(j), i);
					sum = sum + (result * result);
				}
				System.out.println("Dcoument " + i + ": " + Math.sqrt(sum));
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public Double getNormalizationValue(Integer docID) {
		Double sum = 0.0;
		try {
			List<String> wordsInDoc = mapOfEachDocData.get(docID);
			for (int i = 0; i < wordsInDoc.size(); i++) {
				Double result = 0.0;
				try {
					result = tfIDF(wordsInDoc.get(i), docID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sum += (result * result);
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		// System.out.println("DOCID Normalization: "+docID+"::"+ Math.sqrt(sum));
		return sum;
	}

	public void buildQueryIndex(String query) throws IOException {
		// TODO Auto-generated method stub
		String processedQuery = "";
		PreProcessor preProcessor = new PreProcessor();
		Stemmer stemmer = new Stemmer();
		processedQuery = preProcessor.removeHTMLTags(query);
		processedQuery = preProcessor.removeStopWords(processedQuery);
		processedQuery = stemmer.stemmerMain(processedQuery, 0);
		String[] textArray = query.split("\\s+");
		for (String word : textArray) {
			word = word.toLowerCase();
			if (queryTermFrequency.containsKey(word)) {
				queryTermFrequency.put(word, queryTermFrequency.get(word) + 1);
			} else {
				queryTermFrequency.put(word, 1);
			}
		}
	}

}
