package com.farmstory.repository.custom;

import com.farmstory.dto.CSPageRequestDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CsArticleRepositoryCustom {
    public Page<Tuple> selectCsForAdmin(CSPageRequestDTO cspagerequestDTO, Pageable pageable, int cateNo);
    public Page<Tuple> selectCsSearchForAdmin(CSPageRequestDTO cspagerequestDTO, Pageable pageable, int cateNo);

    public Page<Tuple>  selectCsForUser(CSPageRequestDTO cspagerequestDTO, Pageable pageable, String uid);

}
