package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.news.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Integer>, JpaSpecificationExecutor<ArticleTag> {

    ArticleTag findByName(String name);

    @Query("select max(id) from ArticleTag")
    Integer maxId();

    @Modifying
    @Query("delete from ArticleTag a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids")List<Integer> ids);


    @Modifying
    @Query("update ArticleTag set sort = :sort where id = :id")
    void sort(@Param(value = "id") Integer id,@Param(value = "sort") Integer sort);

}
