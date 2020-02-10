package com.biz.friend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.friend.domain.MemberDTO;
import com.biz.friend.persistence.MemberDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	@Autowired
	private BCryptPasswordEncoder pwEnc;
	
	protected final MemberDao mDao;
	
	public int insert(MemberDTO mDTO) {
		String encPW = pwEnc.encode(mDTO.getM_pw());
		mDTO.setM_pw(encPW);
		
		return mDao.insert(mDTO);
	}
	
	public boolean login(MemberDTO mDTO) {
		MemberDTO dbMemberDTO = mDao.selectById(mDTO.getM_id());
		
		String pwFromDB = dbMemberDTO.getM_pw();
		
		boolean login = pwEnc.matches(mDTO.getM_pw(), pwFromDB);
		
		return login;
	}

	public MemberDTO selectById(String m_id) {
		return mDao.selectById(m_id);
	}
	
}
