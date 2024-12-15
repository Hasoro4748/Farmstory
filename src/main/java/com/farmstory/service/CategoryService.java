package com.farmstory.service;

import com.farmstory.repository.CateRepository;
import com.farmstory.dto.CateDTO;
import com.farmstory.entity.Cate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CateRepository cateRepository;

    public CateDTO selectCategory(String group, String name){
        Optional<Cate> cate = cateRepository.findByCateGroupAndCateName(group,name);

        log.info(cate);
        if(cate.isPresent()){
            CateDTO cateDTO = cate.get().toDTO();
            return cateDTO;
        }

        return null;

    }

    public CateDTO selectCateNo(int cateNO){
        Optional<Cate> cate = cateRepository.findById(cateNO);

        log.info(cate);
        if(cate.isPresent()){
            CateDTO cateDTO = cate.get().toDTO();
            return cateDTO;
        }

        return null;
    }


//    public CateDTO findByGroupAndName(String group, String name) {
//
//        Optional<Cate> category = cateRepository.findByCateGroupAndCateName(group, name);
//        log.info(category);
//        if (category.isPresent()) {
//             Cate cate = category.get();
//            return  cate.toDTO();
//        }
//        return null;
//    }



}
