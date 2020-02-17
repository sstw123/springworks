package com.biz.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biz.shop.domain.DeptVO;

public interface DeptDao extends JpaRepository<DeptVO, Long>{

}
