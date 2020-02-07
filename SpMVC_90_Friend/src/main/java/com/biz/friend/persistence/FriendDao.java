package com.biz.friend.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.friend.domain.FriendDTO;

public interface FriendDao {

	@Select("select * from tbl_friend")
	public List<FriendDTO> selectAll();

	@Select("select * from tbl_friend where frd_name LIKE CONCAT('%', #{search_text}, '%') or frd_tel LIKE CONCAT('%', #{search_text}, '%')")
	public List<FriendDTO> searchFromAll(String search_text);

	@Select("select * from tbl_friend where frd_name LIKE CONCAT('%', #{search_text}, '%')")
	public List<FriendDTO> searchFromName(String search_text);

	@Select("select * from tbl_friend where frd_tel LIKE CONCAT('%', #{search_text}, '%')")
	public List<FriendDTO> searchFromTel(String search_text);
	
	@Select("select * from tbl_friend where frd_id = #{frd_id}")
	public FriendDTO selectById(int frd_id);
	
	public int insert(FriendDTO frdDTO);
	public int update(FriendDTO frdDTO);
	
	@Delete("delete from tbl_friend where frd_id = #{frd_id}")
	public int delete(int frd_id);

}
