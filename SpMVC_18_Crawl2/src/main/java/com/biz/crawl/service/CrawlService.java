package com.biz.crawl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.domain.CrawlSubDTO;
import com.biz.crawl.persistence.CrawlDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CrawlService {
	
	/*
	 * 네이버 현재 상영작 리스트에서 영화 제목, 이미지, 순위를 가져오기 위해
	 * - url
	 * - title이 들어있는 tag
	 * - image가 들어있는 tag
	 * - rankList
	 * 를 가져오기 위한 tag 묶음 정보를 변수로 선언
	 */
	private final String tupleBoxTag = "tr.ls.oh.tr";
	
	Document crawlDocument = null;
	Elements dateList = null;
	Elements hitList = null;

	private final CrawlDao crwDao;
	
	public List<CrawlSubDTO> selectAll() {
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
	
	// @Scheduled(cron = "0 30 1 * * *")
	// @Scheduled(fixedRate = 100000)
	@Scheduled(fixedDelay = 100000)
	public void getCrawl() {
		List<CrawlVO> crawlList = this.getCrawlList();
		crwDao.deleteAll();
		crwDao.insertAll(crawlList);
//		for(CrawlVO vo : crawlList) {
//			crwDao.insert(vo);
//		}
	}
	*/
	
	public CrawlDTO getFreeBoard() {
		CrawlDTO crawlDTO = new CrawlDTO();
		String crawlSiteURL = "http://www.inven.co.kr/board/hs/3509";
		crawlDTO.setCrawlSiteURL(crawlSiteURL);
		crawlDTO.setNextPageSiteURL(crawlSiteURL + "?sort=PID&p=");
		crawlDTO.setDateTag(".ls.oh.tr td.date");
		crawlDTO.setHitTag(".ls.oh.tr td.hit");
		
		return this.getCrawlDTO(crawlDTO);
	}
	
	public void setCrawl(CrawlDTO crawlDTO) {
		// 1. URL에 해당하는 html 페이지코드 가져오기
		// 2. Document 클래스에 담기
		// 3. jsoup의 Document 클래스를 사용하여 DOM 형식의 Document 만들기
		
		try {
			crawlDocument = Jsoup.connect(crawlDTO.getCrawlSiteURL()).get();//Document 클래스에 페이지 코드 담김
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// DOM에서 dateTag 문자열 기준으로 리스트 추출
		dateList = crawlDocument.select(crawlDTO.getDateTag());
		// DOM에서 hitTag 문자열 기준으로 리스트 추출
		hitList = crawlDocument.select(crawlDTO.getHitTag());
		
	}
	
	public CrawlDTO getCrawlDTO(CrawlDTO crawlDTO) {
		
		this.setCrawl(crawlDTO);
		
		// JRE 1.7 이상에서는 생성자 Generic 생략 가능
		List<CrawlSubDTO> crawlSubList = new ArrayList();
		
		int index = 0;
		int tupleIndex = 0;
		int page = 1;
		String date = "";
		String hit = "";
		int sumOfHit = 0;
		
		log.debug("조회수 시작 =============" + sumOfHit);
		
		while(true) {
			
			// dateTag에 담긴 text를 추출하기 = 날짜
			// hitTag의 text 추출 = 조회수
			try {
				date = dateList.get(tupleIndex).text();
				hit = hitList.get(tupleIndex).text();
				if(date.equals("01-31")) break;
			} catch (Exception e) {
				page++;
				crawlDTO.setCrawlSiteURL(crawlDTO.getNextPageSiteURL() + page);
				this.setCrawl(crawlDTO);
				tupleIndex = 0;
				
				date = dateList.get(tupleIndex).text();
				hit = hitList.get(tupleIndex).text();
				if(date.equals("01-31")) break;
			}
			
			if(index > 2) {
				sumOfHit += Long.valueOf(hit);
			}
			
			crawlSubList.add(
				CrawlSubDTO.builder()
					.date(date)
					.hit(hit)
					.build()
			);
			
			tupleIndex++;
			index++;
		} // for end
		
		log.debug("조회수 끝 ==============" + sumOfHit);
		crawlDTO.setSumOfHit(sumOfHit);
		crawlDTO.setCrawlSubList(crawlSubList);
		
		return crawlDTO;
		
	}
	
}
