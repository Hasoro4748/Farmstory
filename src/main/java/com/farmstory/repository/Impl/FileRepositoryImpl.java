package com.farmstory.repository.Impl;

import com.farmstory.entity.QArticle;
import com.farmstory.entity.QCate;
import com.farmstory.entity.QFileEntity;
import com.farmstory.entity.QUser;
import com.farmstory.repository.FileRepository;
import com.farmstory.repository.custom.FileRepositoryCustom;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Log4j2
@Repository
public class FileRepositoryImpl implements FileRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QArticle qarticle = QArticle.article;
    private final QUser quser  = QUser.user;
    private final QCate qcate = QCate.cate;
    private final QFileEntity qfileentity = QFileEntity.fileEntity;
    @Override
    public int selectFileCount(int ano) {

        int content = queryFactory
                .select(qfileentity.count())
                .from(qfileentity)
                .where(qfileentity.ano.eq(ano))
                .fetchOne().intValue();


        return content;
    }
}
