package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Integer>, JpaSpecificationExecutor<QuestionGroup> {

    QuestionGroup findByName(String name);

    @Query("select max(id) from QuestionGroup")
    Integer maxId();

    @Modifying
    @Transactional
    @Query("delete from QuestionGroup a where a.id in (?1)")
    void deleteBatch(List<Integer> ids);


    @Modifying
    @Transactional
    @Query("update QuestionGroup set sort = :sort where id = :id")
    void sort(Integer id, Integer sort);

}
