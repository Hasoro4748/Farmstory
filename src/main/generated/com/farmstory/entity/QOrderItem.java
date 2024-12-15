package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderItem is a Querydsl query type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderItem extends EntityPathBase<OrderItem> {

    private static final long serialVersionUID = 495472434L;

    public static final QOrderItem orderItem = new QOrderItem("orderItem");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final NumberPath<Integer> itemPrice = createNumber("itemPrice", Integer.class);

    public final NumberPath<Integer> itemQty = createNumber("itemQty", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> orderItemNo = createNumber("orderItemNo", Integer.class);

    public final NumberPath<Integer> orderNo = createNumber("orderNo", Integer.class);

    public final NumberPath<Integer> pNo = createNumber("pNo", Integer.class);

    public QOrderItem(String variable) {
        super(OrderItem.class, forVariable(variable));
    }

    public QOrderItem(Path<? extends OrderItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderItem(PathMetadata metadata) {
        super(OrderItem.class, metadata);
    }

}

