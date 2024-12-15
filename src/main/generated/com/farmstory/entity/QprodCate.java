package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QprodCate is a Querydsl query type for prodCate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QprodCate extends EntityPathBase<prodCate> {

    private static final long serialVersionUID = -1456914091L;

    public static final QprodCate prodCate = new QprodCate("prodCate");

    public final StringPath prodCateName = createString("prodCateName");

    public final NumberPath<Integer> prodCateNo = createNumber("prodCateNo", Integer.class);

    public QprodCate(String variable) {
        super(prodCate.class, forVariable(variable));
    }

    public QprodCate(Path<? extends prodCate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QprodCate(PathMetadata metadata) {
        super(prodCate.class, metadata);
    }

}

