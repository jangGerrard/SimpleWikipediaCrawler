package kr.ac.kw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import kr.ac.kw.db.CrawlerDao;
import kr.ac.kw.domain.QueueInputData;
import kr.ac.kw.domain.RegularExpress;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private static int DEPTH_TO_SEARCH = 2;
	private String FILE_NAME = "jang.txt";
	private PriorityQueue<QueueInputData> urls;
	private Map<String, QueueInputData> visitedUrls;
	private String keyword ; 
	private CrawlerDao crawlerDao ; 
	
	
	public Crawler(String keyword){
		this.keyword = keyword;
		urls = new PriorityQueue<QueueInputData>();
		visitedUrls = new HashMap<String , QueueInputData>();
		crawlerDao = new CrawlerDao(keyword);
	}
	
	public void setFileStream(){
		File file = new File(FILE_NAME);
		try{
			PrintStream printStream = new PrintStream(new FileOutputStream(file));
			System.setOut(printStream);
			System.setErr(printStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void crawlWebPage(String url , String keyword , int depth){
		Document doc ;
		try {
			
			createTableInDatabase();
			
			doc = Jsoup.connect(url).get();		
			///여기서 일단 토큰화 해서 데이터 베이스ㄴ에 넣는 것을 수행합니다.
			pageToStringExceptTags(doc.text());
			
			Elements  titles = doc.select("#mw-content-text a");
			
//			for (Element element : titles) {
//				String subKeyword = element.text().trim();
//				String reculsiveUrl = element.attr("abs:href");
//				int referenceCount = 1;
//				QueueInputData pushData = new QueueInputData(
//																	subKeyword,
//																	reculsiveUrl,
//																	depth,
//																	referenceCount);
//				urls.add(pushData);
//			}
//			
//			while(urls.size() != 0){
//				QueueInputData pulledData = urls.remove();
//				if(visitedUrls.get(pulledData.getUrl()) == null){
//					visitedUrls.put(pulledData.getUrl().trim(), pulledData);
//					crawlForInsideWhile(pulledData.addDepthCount());
//				} else {
//					visitedUrls.put(pulledData.getUrl(), pulledData.addReferCount());
//				}
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void crawlForInsideWhile(QueueInputData queueInputData){
		if(queueInputData.getDepth() > DEPTH_TO_SEARCH){
			return ;
		}

		
		Document doc ;
		try{
			doc = Jsoup.connect(queueInputData.getUrl()).get();
			
			Elements titles =  doc.select("#mw-content-text a");
			
			for (Element element : titles) {
				String subKeyword = element.text().trim();
				String reculsiveUrl = element.attr("abs:href");
				int referenceCount = 1;
				if(RegularExpress.getInstance().isUrls(reculsiveUrl)){
					QueueInputData pushData = new QueueInputData(
																subKeyword,
																reculsiveUrl,
																queueInputData.getDepth(),
																referenceCount);
					urls.add(pushData);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void pageToStringExceptTags(String docText ){
		String pageText = RegularExpress.getInstance().getTextExceptTag(docText);
		StringTokenizer stz = new StringTokenizer(pageText);
		List<String> tokens = new ArrayList<String>();
		while(stz.hasMoreTokens()){
			tokens.add(stz.nextToken());
		}
		
		System.out.println("Tokens Size : "+tokens.size());
		
		storeInDatabase(tokens );		
	}
	
	public void createTableInDatabase(){
		//create table name by keyword
		crawlerDao.makeTable();
		
	}

	public void storeInDatabase(List<String> tokens) {
		try {
			crawlerDao.insertTokens(tokens);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
