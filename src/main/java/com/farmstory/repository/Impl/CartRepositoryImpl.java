package com.farmstory.repository.Impl;

import com.farmstory.entity.Cart;
import com.farmstory.entity.QCart;
import com.farmstory.entity.QProduct;
import com.farmstory.entity.QUser;
import com.farmstory.repository.CartRepository;
import com.farmstory.repository.custom.CartRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Repository
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCart qCart = QCart.cart;
    private final QUser qUser  = QUser.user;
    private final QProduct qProduct = QProduct.product;

    @Override
    public List<Tuple> findByCartNo(String cartNo) {

        List<Tuple> cart = queryFactory
                .select(qCart, qProduct)
                .from(qCart)
                .join(qProduct)
                .on(qCart.prodNo.eq(qProduct.pNo))
                .join(qUser)
                .on(qCart.uid.eq(qUser.uid))
                .where(qUser.uid.eq(cartNo))
                .orderBy(qCart.cartNo.asc())
                .fetch();
        return cart;
    }
}
