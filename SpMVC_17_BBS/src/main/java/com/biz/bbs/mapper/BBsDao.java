package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.BBsVO;

public interface BBsDao {
	
	@Select("select * from tbl_bbs order by bbs_date desc, bbs_time desc")
	public List<BBsVO> selectAll();
	
	public List<BBsVO> selectMain();
	
	@Select("select * from tbl_bbs where bbs_id = #{bbs_id}")
	public BBsVO findById(long bbs_id);

	public List<BBsVO> findBySubject(String subject);

	public List<BBsVO> findByWriter(String writer);

	public List<BBsVO> findBySubAndWriter(String subject, String writer);

	public int save(BBsVO bbsVO);
	@Delete("delete from tbl_bbs where bbs_id = #{bbs_id}")
	public int delete(long bbs_id);

	public int insert(BBsVO bbsVO);

	public int update(BBsVO bbsVO);
	
	

}
