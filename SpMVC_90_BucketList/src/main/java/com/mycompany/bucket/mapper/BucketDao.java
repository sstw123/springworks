package com.mycompany.bucket.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mycompany.bucket.model.BucketDTO;
import com.mycompany.bucket.model.PaginationDTO;

public interface BucketDao {
	
	@Select("SELECT * FROM tbl_bucket ORDER BY b_order")
	public List<BucketDTO> selectAll();
	
	@Select("SELECT * FROM tbl_bucket WHERE b_success = true ORDER BY b_order")
	public List<BucketDTO> selectSuccessTrue();
	
	@Select("SELECT * FROM tbl_bucket WHERE b_success = false ORDER BY b_order")
	public List<BucketDTO> selectSuccessFalse();
	
	@Select("SELECT COUNT(*) FROM tbl_bucket")
	public int countAll();
	//페이지네이션
	public List<BucketDTO> selectAllByPage(PaginationDTO pageDTO);
	
	@Select("SELECT * FROM tbl_bucket WHERE b_id = #{b_id}")
	public BucketDTO findById(int b_id);
	
	@Select("SELECT MAX(b_order) FROM tbl_bucket")
	public Integer getMaxOrder();
	
	public int insert(BucketDTO bucketDTO);
	public int update(BucketDTO bucketDTO);
	public int orderMinusOne(int b_order);
	public int orderChange(@Param("b_order")int b_order, @Param("b_order2")int b_order2);
	
	@Delete("DELETE FROM tbl_bucket WHERE b_id = #{b_id}")
	public int delete(int b_id);
	
	

}
