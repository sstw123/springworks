package com.biz.shop.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biz.shop.domain.Authorities;

@Repository
public interface AuthRepository extends JpaRepository<Authorities, String>{

}