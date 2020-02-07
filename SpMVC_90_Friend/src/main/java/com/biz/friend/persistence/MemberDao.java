package com.biz.friend.persistence;

import org.apache.ibatis.annotations.Select;

import com.biz.friend.domain.MemberDTO;

public interface MemberDao {
	
	@Select("select * from tbl_member where m_id = #{m_id}")
	public MemberDTO selectById(String m_id);
	
	public int insert(MemberDTO mDTO);
	public int update(MemberDTO mDTO);

}
