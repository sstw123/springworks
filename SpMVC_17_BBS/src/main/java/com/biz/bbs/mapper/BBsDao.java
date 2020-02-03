package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.BBsVO;

public interface BBsDao {
	
	@Select("select * from tbl_bbs order by bbs_date desc, bbs_time desc")
	public List<BBsVO> selectAll();

}
