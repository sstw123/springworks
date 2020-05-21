package com.biz.shop.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.biz.shop.domain.FileVO;

public interface FileUploadDao {
	
	@Select("SELECT * FROM tbl_file")
	public List<FileVO> selectAll();
	
	@Select("SELECT * FROM tbl_file WHERE id = #{id}")
	public FileVO findById(long id);
	
	@Select("SELECT * FROM tbl_file WHERE file_p_code = #{file_p_code}")
	public List<FileVO> findByPCode(String file_p_code);
	
	@Insert("INSERT INTO tbl_file (file_p_code, file_origin_name, file_upload_name) "
			+ "VALUES (#{file_p_code}, #{file_origin_name}, #{file_upload_name}) " )
	public int insert(FileVO productFileVO);
	public int update(FileVO productFileVO);
	
	// PK로 검색한 파일 1개 삭제
	@Delete("DELETE FROM tbl_file WHERE id = #{id}")
	public int deleteById(long id);
	
	// 상품코드와 연결된 모든 파일 삭제
	@Delete("DELETE FROM tbl_file WHERE file_p_code = #{file_p_code}")
	public int deleteByPCode(String file_p_code);
	
}
