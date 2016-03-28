package kr.ac.kw.domain;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpress {
	private static  RegularExpress regularExpress;
	
	private Matcher matcher;
	
	private Pattern SCRIPTS;
	private Pattern STYLE;
	private Pattern TAGS;
	private Pattern nTAGS;
	private Pattern ENTITY_REFS;
	private Pattern WHITESPACE;
	private Pattern URLS ;
	
	private RegularExpress(){
		SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)script>",Pattern.DOTALL);
		STYLE = Pattern.compile("<style[^>]*>.*</style>");
		TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		ENTITY_REFS = Pattern.compile("&[^;]+;");
		WHITESPACE = Pattern.compile("\\s\\s+");
		URLS = Pattern.compile("^http://.*");
		
	}
	
	public static synchronized RegularExpress getInstance(){
		if(regularExpress == null){
			regularExpress = new RegularExpress();
		}
		return regularExpress;
	}
	
	public boolean isUrls(String url){
		matcher = URLS.matcher(url);        

		if(matcher.matches()){			
			return true;
		}
//			System.out.println("Not Url Pattern , Url : "+url+"\n");
		return false;
	}
	
	public String getTextExceptTag(String content){
		matcher = SCRIPTS.matcher(content);
		content = matcher.replaceAll("");
		matcher = STYLE.matcher(content);
		content = matcher.replaceAll("");
		matcher = TAGS.matcher(content);
		content = matcher.replaceAll("");
		matcher = ENTITY_REFS.matcher(content);
		content = matcher.replaceAll("");
		matcher = WHITESPACE.matcher(content);
		content = matcher.replaceAll("");	
//			tokenizeHtmlPage(content);
//			System.out.println(content);
		return content;
	}
	
	public void tokenizeHtmlPage(String content){
		StringTokenizer tokenizer = new StringTokenizer(content);
		
		System.out.println("num of Tokens"+tokenizer.countTokens());
		
		while(tokenizer.hasMoreTokens()){
			System.out.println("Token : "+tokenizer.nextToken());
		}
	}
}
