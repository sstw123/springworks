package com.biz.memo.persistence;

import java.util.List;

import com.biz.memo.domain.MemoDTO;

public interface MemoDao {
	
	public List<MemoDTO> findAll();
	public MemoDTO findBySeq(long m_seq);
	
	public List<MemoDTO> findByCategory(String m_cate);
	public List<MemoDTO> findBySubject(MemoDTO memoDTO);
	
	public int insert(MemoDTO memoDTO);
	public int update(MemoDTO memoDTO);
	public int delete(long m_seq);

}
