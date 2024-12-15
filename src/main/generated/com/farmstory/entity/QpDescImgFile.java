package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QpDescImgFile is a Querydsl query type for pDescImgFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QpDescImgFile extends EntityPathBase<pDescImgFile> {

    private static final long serialVersionUID = -466206707L;

    public static final QpDescImgFile pDescImgFile = new QpDescImgFile("pDescImgFile");

    public final StringPath p_sName1 = createString("p_sName1");

    public final StringPath p_sName2 = createString("p_sName2");

    public final StringPath p_sName3 = createString("p_sName3");

    public final NumberPath<Integer> pfNo = createNumber("pfNo", Integer.class);

    public final NumberPath<Integer> pNo = createNumber("pNo", Integer.class);

    public final StringPath rdate = createString("rdate");

    public QpDescImgFile(String variable) {
        super(pDescImgFile.class, forVariable(variable));
    }

    public QpDescImgFile(Path<? extends pDescImgFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QpDescImgFile(PathMetadata metadata) {
        super(pDescImgFile.class, metadata);
    }

}

