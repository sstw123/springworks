package com.biz.crawl.controller;

public class main {
	
	public static void main(String[] args) {
		String url = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=181925";
		int urlIndex = url.indexOf("code=") + 5;
		String code = url.substring(urlIndex, urlIndex + 6);
		System.out.println("code : " + code);
	}

}
