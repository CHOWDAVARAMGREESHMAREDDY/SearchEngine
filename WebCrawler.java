import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebCrawler implements Runnable{
	
	//COPIED
	public static final int DEFAULT_PORT = 80;
	public static enum HTTP {
		OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
	};
	/** Version of HTTP used and supported. */
	public static final String version = "HTTP/1.1";
	
	String link;
	//int num;
	private static final int MAX_DEPTH = 100;
	private Thread thread;
	private String first_link;
	ArrayList<String> visited = new ArrayList<>();
	int ID;

	public WebCrawler(String link, int num) {
		super();
		this.link = link;
		this.ID = num;
		
		thread = new Thread(this);
		thread.start();
	}

	public WebCrawler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	private void crawl (int level, String url) {
		if(level <= MAX_DEPTH) {
			// LOGIC
		}
	}
	
	//COPIED
	public static String fetchHTML(String url) throws UnknownHostException, MalformedURLException, IOException {
		URL target = new URL(url);
		String request = craftHTTPRequest(target, HTTP.GET);
		List<String> lines = fetchLines(target, request);

		int start = 0;
		int end = lines.size();

		// Determines start of HTML versus headers.
		while (!lines.get(start).trim().isEmpty() && start < end) {
			start++;
		}

		// Double-check this is an HTML file.
		Map<String, String> fields = parseHeaders(lines.subList(0, start + 1));
		String type = fields.get("Content-Type");

		if (type != null && type.toLowerCase().contains("html")) {
			return String.join(System.lineSeparator(), lines.subList(start + 1, end));
		}

		return null;
	}
	
	public static String craftHTTPRequest(URL url, HTTP type) {
		String host = url.getHost();
		String resource = url.getFile().isEmpty() ? "/" : url.getFile();

		// The specification is specific about where to use a new line
		// versus a carriage return!
		return String.format("%s %s %s\r\n" + "Host: %s\r\n" + "Connection: close\r\n" + "\r\n", type.name(), resource,
				version, host);
	}
	
	public static List<String> fetchLines(URL url, String request) throws UnknownHostException, IOException {
		ArrayList<String> lines = new ArrayList<>();
		int port = url.getPort() < 0 ? DEFAULT_PORT : url.getPort();

		try (Socket socket = new Socket(url.getHost(), port);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				PrintWriter writer = new PrintWriter(socket.getOutputStream());) {
			writer.println(request);
			writer.flush();

			String line = null;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}
	
	public static Map<String, String> parseHeaders(List<String> headers) {
		Map<String, String> fields = new HashMap<>();

		if (headers.size() > 0 && headers.get(0).startsWith(version)) {
			fields.put("Status", headers.get(0).substring(version.length()).trim());

			for (String line : headers.subList(1, headers.size())) {
				String[] pair = line.split(":", 2);

				if (pair.length == 2) {
					fields.put(pair[0].trim(), pair[1].trim());
				}
			}
		}

		return fields;
	}
	
	

}
