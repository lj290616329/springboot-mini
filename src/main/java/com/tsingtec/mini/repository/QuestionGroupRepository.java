package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Integer>, JpaSpecificationExecutor<QuestionGroup> {

    QuestionGroup findByName(String name);

    @Query("select max(id) from QuestionGroup")
    Integer maxId();

    @Modifying
    @Query("delete from QuestionGroup a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids")List<Integer> ids);


    @Modifying
    @Query("update QuestionGroup set sort = :sort where id = :id")
    void sort(@Param(value = "id")Integer id, @Param(value = "sort")Integer sort);

}
