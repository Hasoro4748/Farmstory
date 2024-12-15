package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCate is a Querydsl query type for Cate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCate extends EntityPathBase<Cate> {

    private static final long serialVersionUID = -1668972290L;

    public static final QCate cate = new QCate("cate");

    public final StringPath cateGroup = createString("cateGroup");

    public final StringPath cateName = createString("cateName");

    public final NumberPath<Integer> cateNo = createNumber("cateNo", Integer.class);

    public QCate(String variable) {
        super(Cate.class, forVariable(variable));
    }

    public QCate(Path<? extends Cate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCate(PathMetadata metadata) {
        super(Cate.class, metadata);
    }

}

