package com.biz.crawl.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.crawl.domain.CrawlSubDTO;

public interface CrawlDao {
	
	@Select("select * from tbl_movie")
	public List<CrawlSubDTO> selectAll();
	
	public int insert(CrawlSubDTO naverMovieVO);
	public int insertAll(List<CrawlSubDTO> naverMovieList);
	
	@Delete("delete from tbl_movie")
	public void deleteAll();

}
