package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = -1668972337L;

    public static final QCart cart = new QCart("cart");

    public final NumberPath<Integer> cart_delivery = createNumber("cart_delivery", Integer.class);

    public final NumberPath<Integer> cartNo = createNumber("cartNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> cartProdDate = createDateTime("cartProdDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> cartProdQty = createNumber("cartProdQty", Integer.class);

    public final NumberPath<Integer> delprice = createNumber("delprice", Integer.class);

    public final NumberPath<Integer> prodNo = createNumber("prodNo", Integer.class);

    public final NumberPath<Integer> totalprice = createNumber("totalprice", Integer.class);

    public final StringPath uid = createString("uid");

    public QCart(String variable) {
        super(Cart.class, forVariable(variable));
    }

    public QCart(Path<? extends Cart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCart(PathMetadata metadata) {
        super(Cart.class, metadata);
    }

}

