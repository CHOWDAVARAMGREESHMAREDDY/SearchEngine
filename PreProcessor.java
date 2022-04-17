import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PreProcessor {

	/*
	 * public void fileInput1() throws IOException { String[] files = {
	 * "C:\\Users\\Shankar Ambati\\Desktop\\Information Retrieval\\doc1.txt",
	 * "C:\\Users\\Shankar Ambati\\Desktop\\Information Retrieval\\sample1.txt", };
	 * int docId = 0; for (String fileName : files) { try (BufferedReader br = new
	 * BufferedReader(new FileReader(fileName))) {
	 * 
	 * StringBuilder sb = new StringBuilder(); String line; while (((line =
	 * br.readLine()) != null)) { sb.append(" " + line); } String htmlString =
	 * removeHTMLTags(sb.toString()); String stopWordsQuery =
	 * removeStopWords(htmlString); Stemmer s = new Stemmer();
	 * s.stemmerMain(stopWordsQuery, docId); docId++; } catch (IOException e) {
	 * System.out.println("File " + fileName + " not found. Skip it"); } } }
	 */

	public String removeStopWords(String original) throws IOException {
		List<String> stopwords = Files.readAllLines(
				Paths.get("C:\\Users\\s242a803\\Desktop\\IR\\stop_words_english.txt"));

		ArrayList<String> allWords = Stream.of(original.toLowerCase().split(" "))
				.collect(Collectors.toCollection(ArrayList<String>::new));
		allWords.removeAll(stopwords);

		String result = allWords.stream().collect(Collectors.joining(" "));
		return result;
	}

	public String removeHTMLTags(String htmlPage) {
		return htmlPage.replaceAll("<[^>]*>", "")
				.replaceAll("(?i)(?:<[ \\n\\r]*script[^>]*>)(.*?)(?:<[ \\n\\r]*/script[^>]*>)", "");
	}
	
	public String[] split(String text) {
		text = text.trim();
		return text.isEmpty() ? new String[0] : text.split("\\\\W+");
	}

}
