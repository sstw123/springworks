package com.biz.naver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverItemVO {
	
	// NEWS item
	private String title;// "콘크리트연합회, <b>코로나</b>19 피해복구 지원 성금",
	private String originallink;// "http://www.naeil.com/news_view/?id_art=343967",
	private String link;// "http://www.naeil.com/news_view/?id_art=343967",
	private String description;// "성금은 <b>코로나</b>19 발생으로 가장 큰 피해를 입은 대구·경북지역 중소기업과 소외계층에 지원된다. 김동우(왼쪽 세번째) 한국콘크리트공업협동조합 연합회장이 김기문(왼쪽 네번째) 중소기업중앙회장에게 성금을... ",
	private String pubDate;// "Wed, 18 Mar 2020 13:35:00 +0900"
	
	// MOVIE item
	private String image;// "https://ssl.pstatic.net/imgmovie/mdi/mit110/1604/160441_P01_144910.jpg",
	private String subtitle;//: "Lost Cat Corona",
	private String director;// "안토니 타르시타노|",
	private String actor;// "숀 영|지나 거손|랄프 마치오|데이빗 제야스|아담 페라라|제프 코버|폴 소르비노|톰 우팻|",
	private String userRating;// "6.00"
	
	// BOOK item
	private String author;// "세바스찬 알바라도",
	private String price;// "17000",
	private String discount;// "15300",
	private String publisher;// "하이픈",
	private String pubdate;// "20191104",
	private String isbn;// "1190149060 9791190149068"

}
