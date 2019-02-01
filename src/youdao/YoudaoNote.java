package youdao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class YoudaoNote {

	private static String mTitle = null;
	private static final String PREFIX = "C:\\Users\\i042416\\Pictures\\";
	
	private static void createFolder(String title){
		DownloadTask.FOLDER = PREFIX + title;
		File file = new File( DownloadTask.FOLDER);
		file.mkdir();
	}
	
	private static List<DownloadTask> getPicUrlList(String formattedUrl){
		HttpClient client = HttpClients.createDefault();
		List<DownloadTask> resultPic = new ArrayList<DownloadTask>();
		int index = 0;
		
	    HttpGet get = new HttpGet(formattedUrl);
	        try {
	            HttpResponse response = client.execute(get);
	            HttpEntity entity = response.getEntity();
	            String result = EntityUtils.toString(entity, "UTF-8");
	            JSONObject obj = new JSONObject(result);
	            
	            mTitle = obj.get("tl").toString();
	            createFolder(mTitle);
	            String content = obj.get("content").toString();
	            // System.out.println("content: " + content);
	            
	            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(content);
	            while (m.find()) {
	                    // System.out.println(m.group(1));
	                    DownloadTask task = new DownloadTask(m.group(1), index++);
	                    resultPic.add(task);
	            }
	        } catch (Exception e){
	        	e.printStackTrace();
	        }
	        return resultPic;
	}
	
	private static void start(List<DownloadTask> task){
		if( task.isEmpty()){
			System.out.println("No picture to download!");
			return;
		}
		System.out.println("Total pic to be downloaded: " + task.size());
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		for( int i = 0; i < task.size(); i++){
			PictureDownloader cc = new PictureDownloader(task.get(i));
			executor.execute(cc);			
		}
		
		executor.shutdown();
		while (!executor.isTerminated()) {
        }
		
		System.out.println("download finished");
	}
	
	public static void main(String[] args) {
		
		String main ="41b4c7bd84f4589c8ad1441eba2408c1";
		String adhoc = "24b223ba96281fecae00e11e07156f3f";
		String urlStr = "http://note.youdao.com/yws/public/note/"
				    // + adhoc
				     + main
				+ "?keyfrom=public";
		
		System.out.println(urlStr);
		List<DownloadTask> task = getPicUrlList(urlStr);
		start(task);
	}
}
