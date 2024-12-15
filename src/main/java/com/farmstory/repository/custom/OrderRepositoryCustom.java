package com.farmstory.repository.custom;

import com.farmstory.dto.PageRequestDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    public Page<Tuple> selectOrderAllForList(PageRequestDTO requestDTO, Pageable pageable);
    public Page<Tuple> selectOrderMyForList(PageRequestDTO requestDTO, Pageable pageable);
}
