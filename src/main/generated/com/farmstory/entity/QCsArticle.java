package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCsArticle is a Querydsl query type for CsArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsArticle extends EntityPathBase<CsArticle> {

    private static final long serialVersionUID = 1830005271L;

    public static final QCsArticle csArticle = new QCsArticle("csArticle");

    public final NumberPath<Integer> cateNo = createNumber("cateNo", Integer.class);

    public final NumberPath<Integer> comment = createNumber("comment", Integer.class);

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdate = createDateTime("createdate", java.time.LocalDateTime.class);

    public final StringPath csNo = createString("csNo");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> file = createNumber("file", Integer.class);

    public final ListPath<FileEntity, QFileEntity> fileList = this.<FileEntity, QFileEntity>createList("fileList", FileEntity.class, QFileEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Integer> inquiry = createNumber("inquiry", Integer.class);

    public final BooleanPath isCompleted = createBoolean("isCompleted");

    public final StringPath regIp = createString("regIp");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final StringPath writer = createString("writer");

    public QCsArticle(String variable) {
        super(CsArticle.class, forVariable(variable));
    }

    public QCsArticle(Path<? extends CsArticle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCsArticle(PathMetadata metadata) {
        super(CsArticle.class, metadata);
    }

}

