package com.biz.iolist.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.iolist.domain.IolistDTO;
import com.biz.iolist.domain.IolistVO;
import com.biz.iolist.persistence.IolistDao;

@Service
public class IolistService {
	
	@Autowired
	SqlSession sqlSession;
	
	private IolistDao ioDao;
	
	// spring에게 ioDao가 필요하니 만들어놓도록 하는 것
	@Autowired
	public void getMapper() {
		ioDao = sqlSession.getMapper(IolistDao.class);
	}
	
	public List<IolistVO> viewFindAll() {
//		IolistDao ioDao = sqlSession.getMapper(IolistDao.class);
		List<IolistVO> iolist = ioDao.viewFindAll();
		return iolist;
	}

	public IolistVO findBySeq(long io_seq) {
//		IolistDao ioDao = sqlSession.getMapper(IolistDao.class);
		IolistVO ioVO = ioDao.findBySeq(io_seq);
		return ioVO;
	}
	
	public IolistDTO findById(String io_seq) {
//		IolistDao ioDao = sqlSession.getMapper(IolistDao.class);
		IolistDTO ioDTO = ioDao.findById(io_seq);
		return null;
	}

	public int insert(IolistDTO ioDTO) {
		IolistDao ioDao = sqlSession.getMapper(IolistDao.class);
		int ret = ioDao.insert(ioDTO);
		return 0;
	}

}
