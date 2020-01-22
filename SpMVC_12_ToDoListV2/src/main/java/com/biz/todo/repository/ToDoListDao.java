package com.biz.todo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.todo.domain.ToDoList;

/*
 * mybatis 3.4.1부터 mapper.xml을 사용하지 않는 새로운 형식이 등장
 * 
 * @Select()
 * @Insert()
 * @Update()
 * @Delete()
 * 
 * Annotation을 interface에 설정하므로써 mapper.xml을 사용하지 않는 새로운 방식의 코드가 가능하다
 * Annotation의 안에 들어가는 SQL문은 문자열로 작성해야 하는데
 * SQL이 복잡할 경우 별도의 String 변수를 불러와서 사용할 수 있다
 * 이 때 SQL문을 작성하는 String 변수는 반드시 final static String 형식으로 작성해야 한다
 * 
 * 하지만 아직은 완전하게 mapper.xml에서 설정하는 모든 코드를 사용하기 어렵다
 * 계속에서 version up이 되면서 점점 xml을 사용하지 않아도 되는 방식으로 발전하고 있다
 */
public interface ToDoListDao {
	
	@Select("select * from tbl_todolist")// mybatis의 mapper를 대신하는 Annotation
	public List<ToDoList> selectAll();
	
	@Select("select * from tbl_todolist where td_seq = #{td_seq}")
	public ToDoList selectBySeq(long td_seq);
	
	@Select("select * from tbl_todolist where td_sub like '%' || #{td_sub} || '%'")
	public List<ToDoList> selectBySub(String td_sub);
	
	@InsertProvider(type=ToDoListSQL.class, method="insert_sql")
	public int insert(ToDoList toDoList);
	
	@UpdateProvider(type = ToDoListSQL.class, method="update_sql")
	public int update(ToDoList toDoList);
	
	@Delete("delete from tbl_todolist where td_seq = #{td_seq}")
	public int delete(long td_seq);
	
	@Update(ToDoListSQL.complete_sql)
	public int complete(@Param("td_seq")long td_seq);
	
	// ToDoListSQL 클래스에 정의된 alarm_sql method를 호출하여
	// 동적 쿼리를 가져와서 Update를 수행하기
	@UpdateProvider(type=ToDoListSQL.class, method="alarm_sql")
	public int alarm(@Param("td_seq")long td_seq);

	
	
}
