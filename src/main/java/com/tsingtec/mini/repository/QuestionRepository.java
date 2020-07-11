package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {

    @Query("select max(id) from Question")
    Integer maxId();

    @Modifying
    @Query("delete from Question a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids")List<Integer> ids);

    List<Question> findByType(String type);

    @Modifying
    @Query("update Question set sort = :sort where id = :id")
    void sort(@Param(value = "id")Integer id,@Param(value = "sort") Integer sort);


    List<Question> findByGroupSort(Integer oldSorts);

    List<Question> findByGroupNameAndBasicOrderBySortAsc(String name, boolean b);
}
