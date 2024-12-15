package com.farmstory.repository;


import com.farmstory.entity.Article;
import com.farmstory.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ArticleRepository  extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {


    public List<Article> findByCateNo(Integer cateNo);
    // 특정 cateNo에 맞는 Article을 페이징으로 가져오는 쿼리 메서드
    public Page<Article> findByCateNo(int cateNo, Pageable pageable);

    public boolean existsArticleByArticleNo(int no);
    public boolean existsArticlesByArticleNoAndWriter(int no, String writer);

}
