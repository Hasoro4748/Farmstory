package com.farmstory.repository.Impl;

import com.farmstory.dto.PageRequestDTO;
import com.farmstory.entity.*;
import com.farmstory.repository.ProductRepository;
import com.farmstory.repository.custom.ProductRepositoryCustom;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QProduct qproduct = QProduct.product;
    private QpDescImgFile qpDescImgFile = QpDescImgFile.pDescImgFile;
    private QprodCate qprodCate = QprodCate.prodCate;



    @Override
    public Page<Tuple> selectProductAllForList(PageRequestDTO requestDTO, Pageable pageable) {
        List<Tuple> products = queryFactory
                                        .select(qproduct,qpDescImgFile.p_sName1,qpDescImgFile.p_sName2,qpDescImgFile.p_sName3, qprodCate)
                                        .from(qproduct)
                                        .leftJoin(qpDescImgFile)
                                        .on(qproduct.pNo.eq(qpDescImgFile.pNo))
                                        .leftJoin(qprodCate)
                                        .on(qprodCate.prodCateNo.eq(qproduct.prodCateNo))
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .orderBy(qproduct.pNo.desc())
                                        .fetch();

        long total = queryFactory.select(qproduct.count())
                .from(qproduct)
                .fetchOne();
        log.info("total 나오니?> "  + total);
        log.info("products 나오니?> "  + products.toString());

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Page<Tuple> selectProductForSearch(PageRequestDTO requestDTO, Pageable pageable) {
        String cateNo = requestDTO.getType();

        BooleanExpression expression = null;
        if(cateNo.equals("1")){
            expression = qproduct.prodCateNo.eq(1);
        }
        else if(cateNo.equals("2")){
            expression = qproduct.prodCateNo.eq(2);
        }
        else if(cateNo.equals("3")){
            expression = qproduct.prodCateNo.eq(3);
        }
        List<Tuple> products = queryFactory
                .select(qproduct,qpDescImgFile.p_sName1,qpDescImgFile.p_sName2,qpDescImgFile.p_sName3, qprodCate)
                .from(qproduct)
                .leftJoin(qpDescImgFile)
                .on(qproduct.pNo.eq(qpDescImgFile.pNo))
                .leftJoin(qprodCate)
                .on(qprodCate.prodCateNo.eq(qproduct.prodCateNo))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qproduct.pNo.desc())
                .fetch();


        long total = queryFactory.select(qproduct.count())
                .from(qproduct)
                .where(expression)
                .fetchOne();

        return new PageImpl<>(products, pageable, total);
    }

    @Override
    public Tuple selectProductByPId(Integer id) {

        Tuple product = queryFactory
                .select(qproduct,qpDescImgFile.p_sName1,qpDescImgFile.p_sName2,qpDescImgFile.p_sName3, qprodCate)
                .from(qproduct)
                .leftJoin(qpDescImgFile)
                .on(qproduct.pNo.eq(qpDescImgFile.pNo))
                .leftJoin(qprodCate)
                .on(qprodCate.prodCateNo.eq(qproduct.prodCateNo))
                .where(qproduct.pNo.eq(id))
                .fetchOne();

        return product;
    }

    @Override
    public List<Tuple> MainViewProduct() {

        List<Tuple> content = queryFactory
                .select(qproduct,qpDescImgFile.p_sName1,qpDescImgFile.p_sName2,qpDescImgFile.p_sName3, qprodCate)
                .from(qproduct)
                .leftJoin(qpDescImgFile)
                .on(qproduct.pNo.eq(qpDescImgFile.pNo))
                .leftJoin(qprodCate)
                .on(qprodCate.prodCateNo.eq(qproduct.prodCateNo))
                .limit(6)
                .orderBy(qproduct.pNo.desc())
                .fetch();
        return content;
    }

}
