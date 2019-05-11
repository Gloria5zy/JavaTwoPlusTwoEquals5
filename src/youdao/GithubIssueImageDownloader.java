package youdao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class GithubIssueImageDownloader {

	private static String mTitle = null;
	private static final String PREFIX = "C:\\Users\\i042416\\Pictures\\";
	private static final String ISSUEURL = "C:\\Users\\i042416\\git\\JavaTwoPlusTwoEquals5\\src\\youdao\\privateIssue.txt";
	
	private static void createFolder(String title){
		DownloadTask.FOLDER = PREFIX + title;
		File file = new File( DownloadTask.FOLDER);
		file.mkdir();
	}
	
	private static List<DownloadTask> getPicUrlList(){
		int index = 0;
		List<DownloadTask> resultPic = new ArrayList<DownloadTask>();
		BufferedReader br = null;
		try {	
			 br = new BufferedReader(new InputStreamReader(new FileInputStream(ISSUEURL ), "utf-8")); 
			 String line = null;
			 while ((line = br.readLine()) != null) {
				line = line.trim();
				DownloadTask task = new DownloadTask( line, index++);
				resultPic.add(task);
			 }
			 br.close();
		}
		catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e1) {
				e1.printStackTrace();
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
		List<DownloadTask> task = getPicUrlList();
		start(task);
	}
}
