package kr.ac.kw.runner;

import java.util.Scanner;

import kr.ac.kw.Crawler;

public class Runner {

	public static void main(String[] args){
		System.out.println("검색어. : ");
		Scanner sc = new Scanner(System.in);
		String word = sc.next();
		String url = "http://ko.wikipedia.org/wiki/"+word;
		Crawler crawler = new Crawler (word);
		
		crawler.crawlWebPage(url, word, 0);
		
	}
}
