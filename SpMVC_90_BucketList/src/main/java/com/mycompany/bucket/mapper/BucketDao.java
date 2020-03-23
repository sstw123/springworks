package com.mycompany.bucket.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.mycompany.bucket.model.BucketDTO;

public interface BucketDao {
	
	@Select("select * from tbl_bucket")
	public List<BucketDTO> selectAll();
	
	@Select("SELECT * FROM tbl_bucket WHERE b_id = #{b_id}")
	public BucketDTO findById(int b_id);
	
	@Select("select max(b_order) from tbl_bucket")
	public Integer getMaxOrder();
	
	public int insert(BucketDTO bucketDTO);
	public int update(BucketDTO bucketDTO);
	public int updateOrder(int b_id);
	
	@Delete("DELETE FROM tbl_bucket WHERE b_id = #{b_id}")
	public int delete(int b_id);

}
