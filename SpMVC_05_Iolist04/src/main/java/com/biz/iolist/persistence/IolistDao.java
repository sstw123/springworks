package com.biz.iolist.persistence;

import java.util.List;

import com.biz.iolist.domain.IolistDTO;
import com.biz.iolist.domain.IolistVO;

public interface IolistDao {
	
	public List<IolistVO> findAll();
	public List<IolistVO> viewFindAll();
	public IolistDTO findById(String io_seq);
	public IolistVO findBySeq(long io_seq);
	public int insert(IolistDTO ioDTO);

}
