package com.farmstory.service;

import com.farmstory.dto.prodCateDTO;
import com.farmstory.entity.prodCate;
import com.farmstory.repository.prodCateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class prodcateService {

    private final prodCateRepository prodCateRepository;
    private final ModelMapper modelMapper;

    public List<prodCateDTO> selectprodcate(){
        List<prodCate> prodCate = prodCateRepository.findAll();
        List<prodCateDTO> prodcateDTOs = new ArrayList<>();
        for (prodCate ProdCate : prodCate) {
            prodcateDTOs.add(modelMapper.map(ProdCate, prodCateDTO.class));
        }
        return prodcateDTOs;
    }
}
