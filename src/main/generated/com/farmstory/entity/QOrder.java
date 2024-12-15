package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -186960001L;

    public static final QOrder order = new QOrder("order1");

    public final NumberPath<Integer> delivery = createNumber("delivery", Integer.class);

    public final NumberPath<Integer> earnPoint = createNumber("earnPoint", Integer.class);

    public final DatePath<java.time.LocalDate> orderDate = createDate("orderDate", java.time.LocalDate.class);

    public final StringPath orderDesc = createString("orderDesc");

    public final NumberPath<Integer> orderNo = createNumber("orderNo", Integer.class);

    public final StringPath payment = createString("payment");

    public final StringPath recAddr1 = createString("recAddr1");

    public final StringPath recAddr2 = createString("recAddr2");

    public final StringPath receipt = createString("receipt");

    public final StringPath recHp = createString("recHp");

    public final StringPath recZip = createString("recZip");

    public final NumberPath<Integer> totalDiscount = createNumber("totalDiscount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final NumberPath<Integer> totalQty = createNumber("totalQty", Integer.class);

    public final StringPath uid = createString("uid");

    public final NumberPath<Integer> usedPoint = createNumber("usedPoint", Integer.class);

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

