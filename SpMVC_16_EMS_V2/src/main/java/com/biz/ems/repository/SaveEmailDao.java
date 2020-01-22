package com.biz.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biz.ems.domain.EmailVO;

@Repository
public interface SaveEmailDao extends JpaRepository<EmailVO, Long> {

	public EmailVO findByEmsSeq(long emsSeq);
	public List<EmailVO> findAllByFromEmail(String from_email);
	public List<EmailVO> findAllByFromEmailOrderByEmsSeqAsc(String from_email);
	
	public List<EmailVO> findAllBySendDateGreaterThan(String from_email);// >
	public List<EmailVO> findAllBySendDateLessThan(String from_email);// <
	
	public List<EmailVO> findAllByFromEmailAndFromName(@Param("from_email")String from_email, @Param("from_name")String from_name);
	public List<EmailVO> findAllByFromNameAndFromEmail(@Param("from_email")String from_email, @Param("from_name")String from_name);

}
