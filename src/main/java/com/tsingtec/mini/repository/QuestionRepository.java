package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {

    @Query("select max(id) from Question")
    Integer maxId();

    @Modifying
    @Transactional
    @Query("delete from Question a where a.id in (?1)")
    void deleteBatch(List<Integer> ids);

    List<Question> findByType(String type);

    @Modifying
    @Transactional
    @Query("update Question set sort = :sort where id = :id")
    void sort(Integer id, Integer sort);


    List<Question> findByGroupSort(Integer oldSorts);

    List<Question> findByGroupNameOrderBySortAsc(String groupName);


}
