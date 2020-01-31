package com.biz.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biz.ems.domain.EmailVO;
import com.biz.ems.repository.SaveEmailDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveMailService {
	
	private final SaveEmailDao emailDao;
	
	public int insert(EmailVO emailVO) {
		emailDao.save(emailVO);
		return 0;
	}
	
	public List<EmailVO> selectAll() {		
		return (List<EmailVO>) emailDao.findAll();
	}
	
	public EmailVO findBySeq(long emsSeq) {
//		EmailVO emailVO = emailDao.findById(emsSeq);
		// EmailVO emailVO = emailDao.findByEms_seq(emsSeq);
		Optional<EmailVO> emailVO = emailDao.findById(emsSeq);
		return (EmailVO) emailVO.get();
	}

	public void delete(long emsSeq) {
		emailDao.deleteById(emsSeq);
	}

	public void update(EmailVO emailVO) {
		emailDao.save(emailVO);
	}

}
