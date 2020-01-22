package com.biz.gallery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import com.biz.gallery.domain.ImageFilesVO;

public interface ImageFilesDao {
	
	@Select("select * from tbl_images")
	public List<ImageFilesVO> selectAll();
	
	@Select("select * from tbl_images where img_file_p_code = #{img_file_seq}")
	public List<ImageFilesVO> selectBySeq(long img_file_seq);
	
	@Select("select * from tbl_images where img_file_p_code = #{img_file_p_code}")
	public List<ImageFilesVO> selectByPCode(long img_file_p_code);
	
	@Delete("delete from tbl_images where img_file_seq = #{img_file_seq}")
	public int delete(long img_file_seq);

	@InsertProvider(type = ImageFileSQL.class, method = "insert_sql")
	public int insert(ImageFilesVO ifVO);
	

}
