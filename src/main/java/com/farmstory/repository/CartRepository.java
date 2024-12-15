package com.farmstory.repository;

import com.farmstory.entity.Cart;
import com.farmstory.repository.custom.CartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, CartRepositoryCustom {
    public List<Cart> findAllByUid(String uid);
}
