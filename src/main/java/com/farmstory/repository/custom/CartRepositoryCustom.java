package com.farmstory.repository.custom;

import com.farmstory.entity.Cart;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CartRepositoryCustom {
    public List<Tuple> findByCartNo(String cartNo);
}
