package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.Product;
import com.farmstory.entity.pDescImgFile;
import com.farmstory.entity.prodCate;
import com.farmstory.repository.ProductRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 중원씨꺼 select 가져옴
    public ProductDTO selectProductById(int pNo) {
        Tuple tuple = productRepository.selectProductByPId(pNo);

        Product product = tuple.get(0, Product.class);
        String p_sName1 = (tuple.get(1, String.class));
        String p_sName2 = (tuple.get(2, String.class));
        String p_sName3 = (tuple.get(3, String.class));
        prodCate prodCate = tuple.get(4, prodCate.class);
        product.setP_sName1(p_sName1);
        product.setP_sName2(p_sName2);
        product.setP_sName3(p_sName3);
        if(p_sName1 == null){
            p_sName1 = "404이미지없음.png";
        }if(p_sName2 == null){
            p_sName2 = "404이미지없음.png";
        }if(p_sName3 == null){
            p_sName3= "404이미지없음.png";
        }
        product.setProdCate(prodCate);

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }

    // 상품 insert
    public int insertProduct(ProductDTO productDTO) {


        Product product = modelMapper.map(productDTO, Product.class);
        prodCate prodcate = prodCate.builder()
                .prodCateNo((productDTO.getProdCateNo()))
                .build();
        product.setProdCate(prodcate);
        log.info(product);

        // 상품 저장 후 저장한 product PNo 반환
        Product savedproduct = productRepository.save(product);
        log.info("saved pno " + savedproduct);

        return savedproduct.getPNo();

    }

    public ProductPageResponseDTO getAllProductsWithAllInfo(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("pNo" , 10);

        Page<Tuple> products = null;
        log.info("너는 되니? " + products);

        if(pageRequestDTO.getType() == null){
            log.info("너니?1 ");
            products = productRepository.selectProductAllForList(pageRequestDTO, pageable);
            log.info("너까지 되니? " + products);
        }
        else{
            /*
            log.info("너니?2 ");
            products = productRepository.selectProductForSearch(pageRequestDTO, pageable);
            log.info("너는 되니2? " + products);
        */
        }

        List<ProductDTO> productList = products.getContent().stream().map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    String p_sName1 = (tuple.get(1, String.class));
                    String p_sName2 = (tuple.get(2, String.class));
                    String p_sName3 = (tuple.get(3, String.class));
            if(p_sName1 == null){
                p_sName1 = "404이미지없음.png";
            }if(p_sName2 == null){
                p_sName2 = "404이미지없음.png";
            }if(p_sName3 == null){
                p_sName3= "404이미지없음.png";
            }
                    prodCate prodCate = tuple.get(4, prodCate.class);
                    product.setP_sName1(p_sName1);
                    product.setP_sName2(p_sName2);
                    product.setP_sName3(p_sName3);
                    product.setProdCate(prodCate);

                    return modelMapper.map(product, ProductDTO.class);
                }).toList();

        log.info("매핑 되니? " + productList);

        int total = (int) products.getTotalElements();

        return ProductPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .productList(productList)
                .total(total)
                .build();
    }

    // order 후 재고 빼기
    public void updateProductStock(int pNo, int stock){
        productRepository.findById(pNo).ifPresent(product -> {
            int resultstock = product.getStock()-stock;
            product.setStock(resultstock);
            productRepository.save(product);
        });
    }
}
