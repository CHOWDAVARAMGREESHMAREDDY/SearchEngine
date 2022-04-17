import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawlerDemo {

	public static Queue<String> queue = new LinkedList<>();

	public static Set<String> marked = new HashSet<>();

	public static String regex = "http[s]*://(\\w+\\.)*(\\w+)";

	public static void bfsAlgo(String root) throws IOException {
		
		queue.add(root);
		
		while(!queue.isEmpty()) {
			String crawledURL = queue.poll();
			if(marked.size()>100) {
				return ;
			}
			boolean OK = false;
			URL url = null;
			BufferedReader br = null;
			while(!OK) {
				try{
					url = new URL(crawledURL);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					OK = true;
				} catch (MalformedURLException e) {
					System.out.println("fsvfs00"+ crawledURL);
					crawledURL = queue.poll();
					OK = false;
				} catch (IOException ioexception) {
					System.out.println("fsvfs11"+ crawledURL);
					crawledURL = queue.poll();
					OK = false;
				}
				
			}
			StringBuilder sb = new StringBuilder();
			while((crawledURL=br.readLine())!=null) {
				sb.append(crawledURL);
			}
			crawledURL = sb.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(crawledURL);
			while(matcher.find()) {
				String w = matcher.group();
				if(!marked.contains(w)) {
					marked.add(w);
					System.out.println("jbnjk value "+w);
					queue.add(w);
				}
			}
		}
		
	}
	
	public void showResults() {
		System.out.println(marked.size());
		for(String s: marked) {
			System.out.println(s);
		}
	}
	

}
