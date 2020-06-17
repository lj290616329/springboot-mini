package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.news.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

    @Modifying
    @Transactional
    @Query("delete from Article a where a.id in (?1)")
    void deleteBatch(List<Integer> ids);

    @Modifying
    @Transactional
    @Query("update Article set sort = :sort where id = :id")
    void sortArticle(Integer id, Integer sort);

    @Query("select max(id) from Article")
    Integer maxId();
}
