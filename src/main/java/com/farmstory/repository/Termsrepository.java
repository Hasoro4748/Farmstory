package com.farmstory.repository;

import com.farmstory.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Termsrepository extends JpaRepository<Terms,Integer> {
}
