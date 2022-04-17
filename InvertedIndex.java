import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InvertedIndex {

	static TreeMap<String, TreeMap<Integer, LinkedList<Integer>>> index = new TreeMap<>();

	static Map<Integer, String> sources = new HashMap<>();

	public static int termFrequency(String word, Integer docID) {
		System.out.println();
		return index.get(word).get(docID).size();
	}

	public static int docFrequency(String word) {
		System.out.println();
		return index.get(word).size();
	}

	public static double invertedDocFrequency(String word) {
		System.out.println();
		return Math.log(sources.size() / docFrequency(word));
	}

	public static double tfIDF(String word, Integer docID) {
		System.out.println();
		return invertedDocFrequency(word) * termFrequency(word, docID);
	}

	public static Map<Integer, List<Double>> docVectors() {
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
				String entireDocText = new String(buffer, StandardCharsets.UTF_8);
				PreProcessor preProcessor = new PreProcessor();
				Stemmer stemmer = new Stemmer();
				entireDocText = preProcessor.removeHTMLTags(entireDocText);
				entireDocText = preProcessor.removeStopWords(entireDocText);
				entireDocText = stemmer.stemmerMain(entireDocText, 0);
				String[] textArray = entireDocText.split("\\s+");
				int k = 0;
				for (String word : textArray) {
					LinkedList<Integer> pos = new LinkedList<>();
					word = word.toLowerCase();
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
			} catch (IOException e) {
				System.out.println("File " + filePath + " not found. Skip it");
			}
			docID++;
		}
		System.out.println();
	}

}
