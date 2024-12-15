package com.farmstory.repository;

import com.farmstory.entity.prodCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface prodCateRepository extends JpaRepository<prodCate, Long> {
}
