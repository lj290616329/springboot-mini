package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.news.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Integer>, JpaSpecificationExecutor<ArticleTag> {

    ArticleTag findByName(String name);

    @Query("select max(id) from ArticleTag")
    Integer maxId();

    @Modifying
    @Transactional
    @Query("delete from ArticleTag a where a.id in (?1)")
    void deleteBatch(List<Integer> ids);


    @Modifying
    @Transactional
    @Query("update ArticleTag set sort = :sort where id = :id")
    void sort(Integer id, Integer sort);

}
