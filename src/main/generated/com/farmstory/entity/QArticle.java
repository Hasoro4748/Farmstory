package com.farmstory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 1194800359L;

    public static final QArticle article = new QArticle("article");

    public final NumberPath<Integer> articleNo = createNumber("articleNo", Integer.class);

    public final NumberPath<Integer> cateNo = createNumber("cateNo", Integer.class);

    public final NumberPath<Integer> com = createNumber("com", Integer.class);

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Integer> file = createNumber("file", Integer.class);

    public final ListPath<FileEntity, QFileEntity> fileList = this.<FileEntity, QFileEntity>createList("fileList", FileEntity.class, QFileEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final BooleanPath isNotice = createBoolean("isNotice");

    public final NumberPath<Integer> noticeCate = createNumber("noticeCate", Integer.class);

    public final StringPath regIp = createString("regIp");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final StringPath writer = createString("writer");

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

