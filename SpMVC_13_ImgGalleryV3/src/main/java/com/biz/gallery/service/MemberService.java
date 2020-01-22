package com.biz.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.gallery.domain.MemberVO;
import com.biz.gallery.repository.MemberDao;

@Service
public class MemberService {
	
	private final MemberDao memberDao;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public MemberService(MemberDao memberDao, BCryptPasswordEncoder passwordEncoder) {
		this.memberDao = memberDao;
		this.passwordEncoder = passwordEncoder;
	}
	// -------------------------- bean 생성자 끝-----------------------------
	
	public int insert(MemberVO memberVO) {
		// 입력된 회원정보중 password를 암호화하기
		String cryptPW = passwordEncoder.encode(memberVO.getU_pw());
		memberVO.setU_pw(cryptPW);
		
		int nCount = memberDao.memberCount();
		if(nCount < 1) {
			memberVO.setU_grade("ADMIN");// 최초 사용자의 grade
		} else if (nCount < 4) {
			memberVO.setU_grade("MEM");// 2,3번째 사용자의 grade
		} else {
			memberVO.setU_grade("GUEST");// 그 이후 사용자의 grade
		}
		memberVO.setU_name(memberVO.getU_id());
		
		return memberDao.insert(memberVO);
	}

	public MemberVO login(MemberVO memberVO) {
		
		String req_u_id = memberVO.getU_id();
		String req_u_pw = memberVO.getU_pw();
		
		MemberVO dbMemberVO = memberDao.selectById(req_u_id);
		
		if(dbMemberVO != null) {
			String dbPW = dbMemberVO.getU_pw();
			if(passwordEncoder.matches(req_u_pw, dbPW)) {
				return dbMemberVO;
			}
		} 
		
		return null;
	}
	
}
