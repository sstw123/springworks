package com.biz.bbs.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;

@Service
public class FileReaderService {
	
	public List<BBsVO> getBBsData() {
		
		ClassPathResource cpr = new ClassPathResource("bbs_data.txt");
		
		Path path = null;
		
		List<BBsVO> bbsList = new ArrayList<BBsVO>();
		
		try {
			path = Paths.get(cpr.getURI());
			List<String> lines = Files.readAllLines(path);
			
			for(String line : lines) {
				String[] items = line.split(":");
				
				BBsVO bbsVO = BBsVO.builder()
						.b_id(Long.valueOf(items[0]))
						.b_p_id(Long.valueOf(items[1]))
						.b_writer(items[2])
						.b_date_time(items[3] + ":" + items[4] + ":" + items[5])
						.b_subject(items[6])
						.b_content(items[7])
						.build();
				
				bbsList.add(bbsVO);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bbsList;
	}// getBBsData()
	
	public List<BBsVO> getMain(List<BBsVO> bbsList) {
		
		
		// List 객체에서 stream 객체 추출
		Stream<BBsVO> bbsStream = bbsList.stream();
		// stream은 filter 메소드 사용 가능, filter 메소드를 이용해 조건에 맞는 아이템만 추출
		List<BBsVO> pList = bbsStream
			.filter(vo->vo.getB_p_id() == 0)
			.collect(Collectors.toList());
		
		return pList;
	}
	
	public List<BBsVO> getReply(List<BBsVO> bbsList, BBsVO bbsVO, int depth) {
		List<BBsVO> tempList = new ArrayList<BBsVO>();
		List<BBsVO> replyList = new ArrayList<BBsVO>();
		
		if(depth > 0) {
			String b_subject = "";
		
			for(int i = 0; i < depth; i++) {
				b_subject += "&nbsp;&nbsp;&nbsp;";
			}
			b_subject += "<i class='fas fa-hand-point-right'></i>&nbsp;" + bbsVO.getB_subject();
			bbsVO.setB_subject(b_subject);
		}
		
		replyList.add(bbsVO);
		// 1-1. 답글리스트에 부모글 add
		
		tempList = bbsList
					.stream()
					.filter(vo->vo.getB_p_id() == bbsVO.getB_id())
					.collect(Collectors.toList());
		// 1-2. 답글의 답글들 임시 리스트에 담기(for문 안쓰고 담기)
		// 순서 : bbsList(글 리스트)에서 stream 객체 추출
		// -> filter로 조건 설정(매개변수 글 리스트 크기만큼 순회하며 비교하는 글 vo의 b_p_id와, 부모글 bbsVO의 b_id가 같은지 검사하여 부모글의 답글만 추출)
		// -> collect 메소드와 Collectors.toList()로 List에 담기
		
		if(tempList.size() < 1) return replyList;
		// 1-3. 부모글의 답글이 존재하지 않는 경우(tempList의 크기가 0) 밑으로 진행하지 않고 바로 replyList 반환(= 원글만 담겨있음)
		// --------------------------------------
		// 1-4. 부모글의 답글이 존재할 경우
		tempList.forEach(vo -> {
			replyList.addAll(this.getReply(bbsList, vo, 0));
			// 또 다시 tempList의 vo 하나마다 { } 내부 코드 실행
			// 1-5. 모든 글 중에서 vo를 부모글로 가진 글(자식답글)들 가져와서 replyList에 추가하기
		});
		
		// 여기까지 끝났다면 하나의 부모글에 달린 모든 답글과, 답글의 답글들이, replyList에 추가되어 있음
		
		return replyList;
	}

}
