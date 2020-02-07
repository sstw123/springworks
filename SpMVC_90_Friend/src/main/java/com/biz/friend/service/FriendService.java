package com.biz.friend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.friend.domain.FriendDTO;
import com.biz.friend.domain.SearchDTO;
import com.biz.friend.persistence.FriendDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendService {
	
	protected final FriendDao frdDao;
	
	public List<FriendDTO> selectAll() {
		return frdDao.selectAll();
	}

	public int insert(FriendDTO frdDTO) {
		return frdDao.insert(frdDTO);
	}

	public List<FriendDTO> search(SearchDTO searchDTO) {
		
		List<FriendDTO> searchResult = new ArrayList();
		
		if(searchDTO.getSearch_opt().equalsIgnoreCase("all")) {
			searchResult = frdDao.searchFromAll(searchDTO.getSearch_text());
		} else if(searchDTO.getSearch_opt().equalsIgnoreCase("name")) {
			searchResult = frdDao.searchFromName(searchDTO.getSearch_text());
		} else if(searchDTO.getSearch_opt().equalsIgnoreCase("tel")) {
			searchResult = frdDao.searchFromTel(searchDTO.getSearch_text());
		}
		
		return searchResult;
		
	}

	public FriendDTO selectById(int frd_id) {
		return frdDao.selectById(frd_id);
	}

	public int update(FriendDTO frdDTO) {
		return frdDao.update(frdDTO);
	}

	public int delete(int frd_id) {
		return frdDao.delete(frd_id);
	}

}
