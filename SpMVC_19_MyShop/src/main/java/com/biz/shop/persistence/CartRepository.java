package com.biz.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biz.shop.domain.CartVO;

// extends JpaRepositody<사용할 VO, PK Wrapper Type>
public interface CartRepository extends JpaRepository<CartVO, Long> {

}
