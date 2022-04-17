import java.io.IOException;

public class StartSearchEngine {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String[] files = { "C:\\Users\\s242a803\\Desktop\\IR\\doc1.txt", "C:\\Users\\s242a803\\Desktop\\IR\\doc2.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\sample1.txt", "C:\\Users\\s242a803\\Desktop\\IR\\sample2.txt" };
		String[] files1 = { "C:\\Users\\s242a803\\Desktop\\IR\\ir_doc1.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\ir_doc2.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\ir_doc3.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\ir_doc4.txt"
				 };
		String[] files2 = {"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\20ng-train-all-terms.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\20ng-train-no-short.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\20ng-train-no-stop.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\20ng-train-stemmed.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\cade-train-stemmed.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r8-train-all-terms.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r8-train-no-short.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r8-train-no-stop.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r8-train-stemmed.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r52-train-all-terms.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r52-train-no-short.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r52-train-no-stop.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\r52-train-stemmed.txt",
				"C:\\Users\\s242a803\\Desktop\\IR\\TrainSet\\webkb-train-stemmed.txt"};
		
		WebCrawler wc = new WebCrawler();
		/*
		 * InvertedIndex1 idx = new InvertedIndex1();
		 * 
		 * 
		 * idx.buildIndex(files2);
		 * idx.buildQueryIndex("earnings dean foods co expects earnings for the fourth"
		 * ); idx.queryVector(); //idx.temp(files); idx.caluclateSimilarity();
		 */
		//System.out.println(wc.fetchHTML("https://www.scientecheasy.com/2020/07/oops-concepts-in-java.html/"));
		WebCrawlerDemo dummy = new WebCrawlerDemo();
		//dummy.bfsAlgo("https://www.scientecheasy.com/2020/07/oops-concepts-in-java.html/");
		dummy.bfsAlgo("https://ku.edu/");
		//dummy.bfsAlgo("https://www.scientecheasy.com/2020/07/oops-concepts-in-java.html/");
		
		dummy.showResults();
		
	}

}
