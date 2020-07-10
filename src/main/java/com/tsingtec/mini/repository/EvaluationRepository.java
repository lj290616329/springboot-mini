package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>, JpaSpecificationExecutor<Evaluation> {
    List<Evaluation> findByDid(Integer did);

    @Query(value="select e.* from wx_common_user u left join wx_information i on u.id = i.uid left join mini_evaluation e on e.uid = i.id where u.id=:uid order by e.id desc",nativeQuery = true)
    List<Evaluation> findByUid(@Param(value = "uid")Integer uid);
}
