package com.biz.gallery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.gallery.domain.ImageVO;

public interface ImageDao {
	
	@Select("select * from tbl_gallery")
	public List<ImageVO> selectAll();
	
	// before가 false면 insert가 수행된 다음
	// @SelectKey(keyProperty = "img_seq", keyColumn = "img_seq", resultType = Long.class, statement = "SELECT * from tbl_gallery where img_seq = #{img_seq}", before=false)
	@SelectKey(keyProperty = "img_seq", keyColumn = "img_seq", resultType = Long.class, statement = "SELECT seq_gallery.nextval from dual", before=true)
	@InsertProvider(type=ImageSQL.class, method="insert_sql")
	public int insert(ImageVO imageVO);

	@Select("select * from tbl_gallery where img_seq = #{img_seq}")
	public ImageVO selectBySeq(String img_seq);

	@UpdateProvider(type=ImageSQL.class, method="update_sql")
	public int update(ImageVO imageVO);
	
	@Delete("delete from tbl_gallery where img_seq = #{img_seq}")
	public int delete(String img_seq);

}
