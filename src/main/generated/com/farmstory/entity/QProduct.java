package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1617704160L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> delivery = createNumber("delivery", Integer.class);

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final StringPath pDesc = createString("pDesc");

    public final StringPath pName = createString("pName");

    public final NumberPath<Integer> pNo = createNumber("pNo", Integer.class);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> prodCateNo = createNumber("prodCateNo", Integer.class);

    public final DatePath<java.time.LocalDate> rdate = createDate("rdate", java.time.LocalDate.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

