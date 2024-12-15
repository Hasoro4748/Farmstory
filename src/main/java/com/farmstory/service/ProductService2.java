package com.farmstory.service;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.dto.ProductPageResponseDTO;
import com.farmstory.entity.Product;
import com.farmstory.entity.prodCate;
import com.farmstory.repository.ProductRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService2 {

    @Value("${spring.servlet.multipart.location}")
    private String uploadedPath;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductDTO selectProduct(int pNo) {
        Optional<Product> product = productRepository.findById(pNo);

        if(product.isPresent()){
            ProductDTO productDTO = null;
            productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
        }
        return null;
    }

    public ProductPageResponseDTO getAllProductsWithAllInfo(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("pNo" , 10);


        Page<Tuple> products = null;

        if(pageRequestDTO.getType() == null){
            products = productRepository.selectProductAllForList(pageRequestDTO, pageable);
        }
        else{

            products = productRepository.selectProductForSearch(pageRequestDTO, pageable);

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

        int total = (int) products.getTotalElements();

        return ProductPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .productList(productList)
                .total(total)
                .build();
    }

    public ProductDTO selectProductById(int pNo) {
            Tuple tuple = productRepository.selectProductByPId(pNo);

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

            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            return productDTO;
    }
    public List<ProductDTO> mainProduct(){
        List<Tuple> products = productRepository.MainViewProduct();
        if(!products.isEmpty()){
            List<ProductDTO> productList = products.stream().map(tuple -> {
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
            return productList;
        }
        return null;
    }
    public void deleteProductById(int pNo) {
        productRepository.deleteById(pNo);
    }
}
