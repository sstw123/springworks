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
import com.biz.crawl.domain.PaginationDTO;
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
	
	Elements dateList = null;
	Elements hitList = null;
	Elements bbsNoList = null;

	private final CrawlDao crawlDao;
	
	// Read
	public CrawlDTO selectByOptions(CrawlDTO crawlDTO) {
		
		List<CrawlSubDTO> crawlSubList = crawlDao.selectByOptions(crawlDTO);// 사이트,게시판,검색날짜로 검색한 결과값 리스트 전부 가져오기
		
		long sumOfHit = crawlDao.sumOfHitByOptions(crawlDTO);
		long avgOfHit = 0;
		int count = crawlDao.countByOptions(crawlDTO);
		if(count > 0) {
			avgOfHit = sumOfHit / count;
		}
		
		crawlDTO.setSumOfHit(sumOfHit);//총 조회수
		crawlDTO.setAvgOfHit(avgOfHit);//평균 조회수
		crawlDTO.setNumOfTuples(count);//게시물 수
		crawlDTO.setCrawlSubList(crawlSubList);//crawlDTO에 crawlSubList 등록하기
		
		return crawlDTO;
	}
	
	public CrawlDTO selectByOptionsByPage(CrawlDTO crawlDTO) {
		
		List<CrawlSubDTO> crawlSubList = crawlDao.selectByOptionsByPage(crawlDTO);// 사이트,게시판,검색날짜로 검색한 결과값 리스트 페이지별로 가져오기
		
		long sumOfHit = crawlDao.sumOfHitByOptions(crawlDTO);
		long avgOfHit = 0;
		int count = crawlDao.countByOptions(crawlDTO);
		if(count > 0) {
			avgOfHit = sumOfHit / count;
		}
		
		crawlDTO.setSumOfHit(sumOfHit);//총 조회수
		crawlDTO.setAvgOfHit(avgOfHit);//평균 조회수
		crawlDTO.setNumOfTuples(count);//게시물 수
		crawlDTO.setCrawlSubList(crawlSubList);//crawlDTO에 crawlSubList 등록하기
		
		return crawlDTO;
	}
	
	public long countByOptions(CrawlDTO crawlDTO) {
		return crawlDao.countByOptions(crawlDTO);
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
	
	public void insertLOLInvenUserInfo() {
		CrawlDTO crawlDTO = new CrawlDTO();
		String crawlSiteURL = "http://www.inven.co.kr/board/lol/2778";//크롤링 할 사이트 URL 설정
		crawlDTO.setC_site("롤인벤");//DB에 넣을 사이트이름 설정
		crawlDTO.setC_board("실시간 유저 정보");//DB에 넣을 게시판이름 설정
		
		this.setInvenCrawlAndInsert(crawlDTO, crawlSiteURL);
	}
	
	public void insertLOLInvenTip() {
		CrawlDTO crawlDTO = new CrawlDTO();
		String crawlSiteURL = "http://www.inven.co.kr/board/lol/2766";
		crawlDTO.setC_site("롤인벤");//DB에 넣을 사이트이름 설정
		crawlDTO.setC_board("팁과노하우");//DB에 넣을 게시판이름 설정
		
		this.setInvenCrawlAndInsert(crawlDTO, crawlSiteURL);
	}
	
	public void insertLOLInvenFreeBoard() {
		CrawlDTO crawlDTO = new CrawlDTO();
		String crawlSiteURL = "http://www.inven.co.kr/board/lol/4626";
		crawlDTO.setC_site("롤인벤");//DB에 넣을 사이트이름 설정
		crawlDTO.setC_board("자유게시판");//DB에 넣을 게시판이름 설정
		
		this.setInvenCrawlAndInsert(crawlDTO, crawlSiteURL);
	}
	
	public void insertHSInvenFreeBoard() {
		CrawlDTO crawlDTO = new CrawlDTO();
		String crawlSiteURL = "http://www.inven.co.kr/board/hs/3509";
		crawlDTO.setC_site("하스인벤");//DB에 넣을 사이트이름 설정
		crawlDTO.setC_board("자유게시판");//DB에 넣을 게시판이름 설정
		
		this.setInvenCrawlAndInsert(crawlDTO, crawlSiteURL);
	}
	
	// Insert,Update
	protected void setInvenCrawlAndInsert(CrawlDTO crawlDTO, String crawlSiteURL) {
		crawlDTO.setSrchStartDate("10-31");//마주치면 크롤링 DB삽입 중단할 날짜 설정
		
		crawlDTO.setCrawlSiteURL(crawlSiteURL);//크롤링 할 사이트 설정
		crawlDTO.setNextPageSiteURL(crawlSiteURL + "?sort=PID&p=");//크롤링 할 사이트 2페이지부터 쿼리 설정
		crawlDTO.setBbsNoTag(".ls.tr td.bbsNo");//가져올 글번호 HTML 태그 선택자 설정
		crawlDTO.setDateTag(".ls.tr td.date");//가져올 작성일자 HTML 태그 선택자 설정
		crawlDTO.setHitTag(".ls.tr td.hit");//가져올 조회수 HTML 태그 선택자 설정
		
		this.insertCrawlDTO(crawlDTO);
	}
	
	protected void insertCrawlDTO(CrawlDTO crawlDTO) {
		
		this.makeCrawlDocument(crawlDTO);// bbsNoList, dateList, hitList 가져오기
		
		// JRE 1.7 이상에서는 생성자 Generic 생략 가능
		List<CrawlSubDTO> crawlSubList = new ArrayList<CrawlSubDTO>();
		
		String c_bbsNo = "";//글번호
		String c_date = "";//작성일
		String str_c_hit = "";//조회수
		
		String c_site = crawlDTO.getC_site();//사이트명
		String c_board = crawlDTO.getC_board();//게시판명
		
		int index = 0;//현재 반복문 인덱스
		int tupleIndex = 0;//한 페이지의 게시글 순서
		int page = 1;//2페이지부터 사이트 URL 쿼리에 넣을 값
		
		while(true) {
			
			// Document(한 페이지)에서
			// dateTag의 text 추출 = 날짜
			// hitTag의 text 추출 = 조회수
			try {
				c_bbsNo = bbsNoList.get(tupleIndex).text();
				c_date = dateList.get(tupleIndex).text();
				str_c_hit = hitList.get(tupleIndex).text();
				if(c_date.equals(crawlDTO.getSrchStartDate())) break;//가져온 날짜 체크해서 튜플 가져오는 반복문 끝내기
			} catch (Exception e) {
				page++;//OutOfBound Exception 발생시 page 숫자를 1 올리기
				crawlDTO.setCrawlSiteURL(crawlDTO.getNextPageSiteURL() + page);//사이트 URL을 쿼리가 포함된 URL로 변경 후 쿼리 값 입력
				this.makeCrawlDocument(crawlDTO);//새로운 URL로 Document 다시 생성 후 dateList와 hitList 값만 가져오고 Document 소멸
				tupleIndex = 0;//게시물 0번부터 다시 시작
				
				c_bbsNo = bbsNoList.get(tupleIndex).text();
				c_date = dateList.get(tupleIndex).text();
				str_c_hit = hitList.get(tupleIndex).text();
				if(c_date.equals(crawlDTO.getSrchStartDate())) break;//가져온 날짜 체크해서 튜플 가져오는 반복문 끝내기
			}
			
			int c_hit = Integer.valueOf(str_c_hit.replace("[^0-9]", ""));//String형 조회수 값을 정규식으로 숫자만 남긴 뒤, int형으로 바꾸기
			
			crawlSubList.add(
				CrawlSubDTO.builder()
					.c_site(c_site)
					.c_board(c_board)
					.c_bbsNo(c_bbsNo)
					.c_date(c_date)
					.c_hit(c_hit)
					.build()
			);
			
			tupleIndex++;//게시글 순서 +1
			index++;//현재 반복문 인덱스 +1
		} // for end
		
		// DB insert/update
		// Site,Board,bbsNo로 검색한 결과가 존재하면 update, 존재하지 않으면 insert
		for(CrawlSubDTO dto : crawlSubList) {
			if(crawlDao.selectByBbsNoSiteBoard(dto) != null) {
				crawlDao.updateDTO(dto);
			} else {
				crawlDao.insertDTO(dto);
			}
		}
		
	}
	
	protected void makeCrawlDocument(CrawlDTO crawlDTO) {
		// 1. URL에 해당하는 html 페이지코드 가져오기
		// 2. Document 클래스에 담기
		// 3. jsoup의 Document 클래스를 사용하여 DOM 형식의 Document 만들기
		Document crawlDocument = null;
		
		try {
			crawlDocument = Jsoup.connect(crawlDTO.getCrawlSiteURL()).get();//사이트 URL를 넣어서 DOM 형식의 Document 통째로 가져오기
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Document(한 페이지)에서 bbsNoTag 선택자 문자열이 포함된 부분 전부 가져오기
		bbsNoList = crawlDocument.select(crawlDTO.getBbsNoTag());
		// Document(한 페이지)에서 dateTag 선택자 문자열이 포함된 부분 전부 가져오기
		dateList = crawlDocument.select(crawlDTO.getDateTag());
		// Document(한 페이지)에서 hitTag 선택자 문자열이 포함된 부분 전부 가져오기
		hitList = crawlDocument.select(crawlDTO.getHitTag());
		
	}

}
