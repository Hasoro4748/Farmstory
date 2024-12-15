package com.farmstory.repository;

import com.farmstory.entity.CsArticle;
import com.farmstory.repository.custom.CsArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsArticleRepository extends JpaRepository<CsArticle, String>, CsArticleRepositoryCustom {

}
