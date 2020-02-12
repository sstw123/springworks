package com.biz.crawl.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.domain.CrawlSubDTO;
import com.biz.crawl.domain.PaginationDTO;

public interface CrawlDao {
	
	@Select("select * from tbl_crawl")
	public List<CrawlSubDTO> selectAll();
	
	public List<CrawlSubDTO> selectByOptions(CrawlDTO crawlDTO);
	
	public CrawlSubDTO selectByBbsNo(CrawlSubDTO crawlSubDTO);
	
	public long countByOptions(CrawlDTO crawlDTO);
	
	public CrawlSubDTO selectByPage(PaginationDTO paginationDTO);
	
	public int insertDTO(CrawlSubDTO crawlSubDTO);
	public int insertList(List<CrawlSubDTO> crawlSubList);
	public int updateDTO(CrawlSubDTO crawlSubDTO);
	
	@Delete("delete from tbl_crawl")
	public void deleteAll();
	
}