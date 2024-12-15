package com.farmstory.repository.custom;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.entity.Product;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    public Page<Tuple> selectProductAllForList(PageRequestDTO requestDTO, Pageable pageable);
    public Page<Tuple> selectProductForSearch(PageRequestDTO requestDTO, Pageable pageable);
    public Tuple selectProductByPId(Integer id);
    public List<Tuple> MainViewProduct();
}
