package com.biz.crawl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.biz.crawl.domain.NaverMovieVO;
import com.biz.crawl.persistence.CrawlDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class NaverMovieCrawlService {
	
	/*
	 * 네이버 현재 상영작 리스트에서 영화 제목, 이미지, 순위를 가져오기 위해
	 * - url
	 * - title이 들어있는 tag
	 * - image가 들어있는 tag
	 * - rankList
	 * 를 가져오기 위한 tag 묶음 정보를 변수로 선언
	 */
	private final String naverMovieURL = "https://movie.naver.com/movie/running/current.nhn";
	private final String rankListTag = "dl.lst_dsc";
	private final String mTitleTag = "dt.tit a";
	private final String mThumbnailTag = "div.thumb a img";

	private final CrawlDao crwDao;
	
	public List<NaverMovieVO> selectAll() {
		return crwDao.selectAll();
	}
	
	/*
	 * @Scheduled
	 * fixedRate : 바로 이전 스케줄링이 시작된 이후에 다시 시작할 시간
	 * fixedDelay : 바로 이전 스케줄링이 종료된 이후 다시 시작할 시간
	 * 
	 * Unix 시스템에서는 일정한 시간(년,월,일,시,분,초)를 지정해서
	 * 어떤 일을 정기적으로 수행할 때 cron tab이라는 기능을 이용해서 작업을 지정할 수 있다
	 * 초, 분, 시, 일, 월, 요일, 년
	 */
	// @Scheduled(cron = "0 30 1 * * *")
	// @Scheduled(fixedRate = 100000)
	@Scheduled(fixedDelay = 100000)
	public void getNaverMovie() {
		List<NaverMovieVO> naverMovieList = this.getMovieRank();
		crwDao.deleteAll();
		crwDao.insertAll(naverMovieList);
//		for(NaverMovieVO vo : naverList) {
//			crwDao.insert(vo);
//		}
	}
	
	public List<NaverMovieVO> getMovieRank() {
		// 1. URL에 해당하는 html 페이지코드 가져오기
		// 2. Document 클래스에 담기
		// 3. jsoup의 Document 클래스를 사용하여 DOM 형식의 Document 만들기
		
		Document naverMovieDoc = null;
		
		try {
			naverMovieDoc = Jsoup.connect(naverMovieURL).get();//Document 클래스에 페이지 코드 담김
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// DOM에서 rankListTag 문자열 기준으로 리스트 추출
		Elements rankList = naverMovieDoc.select(rankListTag);
		// DOM에서 mTitleTag 문자열 기준으로 리스트 추출
		Elements titleList = naverMovieDoc.select(mTitleTag);
		// DOM에서 mThumbnailTag 문자열 기준으로 리스트 추출
		Elements thumbnailList = naverMovieDoc.select(mThumbnailTag);
		
		// JDK 1.7 이상에서는 생성자 Generic 생략 가능
		List<NaverMovieVO> naverMovieList = new ArrayList();
		
		// rankList box들 중 상위 1부터 10번까지만 가져오기
		for(int i = 0; i < 10; i++) {
			
			// dt.tit a에 담긴 text를 추출하기 = 영화 제목
			String m_title = titleList.get(i).text();
			
			// dt.tit a 태그의 href 속성값 추출 = 영화 자세히 보기 URL
			String m_detail_url = titleList.get(i).attr("href");
			m_detail_url = "https://movie.naver.com" + m_detail_url;
			
			/*
			// detailURL에서 영화 코드 추출
			int urlIndex = m_detail_url.indexOf("code=") + 5;
			String code = m_detail_url.substring(urlIndex);
			*/

			// div.thumb a img 태그의 src 속성 값 추출 = 영화 썸네일 이미지 URL
			String m_thumbnail_url = thumbnailList.get(i).attr("src");
			
			// 영화 이미지 URL = 썸네일URL - 쿼리
			// https://movie-phinf.pstatic.net/20200116_23/1579154974413LvtIf_JPEG/movie_image.jpg?type=m99_141_2
			String m_image_url = m_thumbnail_url.substring(0, m_thumbnail_url.lastIndexOf("?"));
			

			
			naverMovieList.add(
				NaverMovieVO.builder()
					.m_rank(i+1)
					.m_title(m_title)
					.m_detail_url(m_detail_url)
					.m_image_url(m_image_url)
					.build()
			);
		} // for end
		
		return naverMovieList;
		
	}
	
}
