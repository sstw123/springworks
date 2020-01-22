package com.biz.gallery.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.gallery.domain.ImageFileVO;

public interface ImageFileDao {
	
	@Select("select * from tbl_images")
	public List<ImageFileVO> selectAll();
	
	@Select("select * from tbl_images where img_file_seq = #{img_file_seq}")
	public ImageFileVO selectBySeq(long img_file_seq);
	
	@Select("select * from tbl_images where img_file_p_code = #{img_file_p_code}")
	public List<ImageFileVO> selectByPCode(long img_file_p_code);
	
	@Delete("delete from tbl_images where img_file_seq = #{img_file_seq}")
	public int delete(long img_file_seq);

	@InsertProvider(type = ImageFileSQL.class, method = "insert_sql")
	public int insert(ImageFileVO ifVO);
	
	@Insert(ImageFileSQL.bulk_insert_sql)
	public int bulk_insert(@Param("ifList") List<ImageFileVO> ifList, @Param("img_p_code") long img_p_code);

}
