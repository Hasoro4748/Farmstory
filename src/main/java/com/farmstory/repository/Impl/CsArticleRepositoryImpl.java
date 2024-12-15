package com.farmstory.repository.Impl;

import com.farmstory.dto.CSPageRequestDTO;
import com.farmstory.entity.QCsArticle;
import com.farmstory.entity.QUser;
import com.farmstory.repository.custom.CsArticleRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CsArticleRepositoryImpl implements CsArticleRepositoryCustom  {

     private final JPAQueryFactory queryFactory;
     private final QCsArticle qcsArticle = QCsArticle.csArticle;
     private final QUser quser = QUser.user;




        @Override
        public Page<Tuple> selectCsForAdmin(CSPageRequestDTO cspagerequestDTO, Pageable pageable, int cateNo) {

            List<Tuple> content = queryFactory
                    .select(qcsArticle,quser.nick)
                    .from(qcsArticle)
                    .join(quser)
                    .on(qcsArticle.writer.eq(quser.uid))
                    .where(qcsArticle.isCompleted.eq(false))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qcsArticle.createdate.asc(), qcsArticle.updateDate.asc())
                    .fetch();

            Long total = queryFactory
                    .select(qcsArticle.count())
                    .from(qcsArticle)
                    .where(qcsArticle.isCompleted.eq(false))
                    .fetchOne();

            return  new PageImpl<Tuple>(content,pageable,total);
    }

    @Override
    public Page<Tuple> selectCsSearchForAdmin(CSPageRequestDTO cspagerequestDTO, Pageable pageable, int cateNo) {

        String type= cspagerequestDTO.getType();
        String keyword= cspagerequestDTO.getKeyword();
        //검색조건에 따라 where조건 표현식 생성
        BooleanExpression expression = null;

        if(type.equals("title")){
            expression = qcsArticle.title.like("%"+keyword+"%");
            log.info("expression : "+expression);
        }else if(type.equals("content")){
            expression = qcsArticle.content.like("%"+keyword+"%");
            log.info("expression : "+expression);

        }else if(type.equals("title_content")){
            expression = qcsArticle.title.like("%"+keyword+"%").or(qcsArticle.content.like("%"+keyword+"%"));
            log.info("expression : "+expression);

        }else if(type.equals("writer")){
            expression = quser.nick.like("%"+keyword+"%");
            log.info("expression : "+expression);

        }

        List<Tuple> content = queryFactory
                .select(qcsArticle, quser.nick)
                .from(qcsArticle)
                .join(quser)
                .on(qcsArticle.writer.eq(quser.uid))
                .where(qcsArticle.isCompleted.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qcsArticle.cateNo.desc())
                .fetch();

        long total = queryFactory
                .select(qcsArticle.count())
                .from(qcsArticle)
                .join(quser)
                .on(qcsArticle.writer.eq(quser.uid))
                .where(qcsArticle.isCompleted.eq(false))
                .fetchOne();


        log.info("total : "+total);
        return new PageImpl<Tuple>(content,pageable,total);
    }

    @Override
    public Page<Tuple> selectCsForUser(CSPageRequestDTO cspagerequestDTO, Pageable pageable, String uid) {
        List<Tuple> content = queryFactory
                .select(qcsArticle,quser.nick)
                .from(qcsArticle)
                .join(quser)
                .on(qcsArticle.writer.eq(quser.uid))
                .where(qcsArticle.writer.eq(uid))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qcsArticle.isCompleted.asc(), qcsArticle.createdate.desc(),qcsArticle.updateDate.desc())
                .fetch();

        Long total = queryFactory
                .select(qcsArticle.count())
                .from(qcsArticle)
                .join(quser)
                .on(qcsArticle.writer.eq(quser.uid))
                .where(qcsArticle.writer.eq(uid))
                .fetchOne();


        return new PageImpl<Tuple>(content,pageable,total);
    }
}
