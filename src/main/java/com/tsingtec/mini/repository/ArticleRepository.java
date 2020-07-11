package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.news.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

    @Modifying
    @Query("delete from Article a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids") List<Integer> ids);

    @Modifying
    @Query("update Article set sort = :sort where id = :id")
    void sort(@Param(value = "id") Integer id,@Param(value = "sort")  Integer sort);

    @Query("select max(id) from Article")
    Integer maxId();

    @Modifying
    @Query(value="update news_article set hits=hits+1 where id= :id",nativeQuery = true)
    void hits(@Param(value = "id") Integer id);
}
