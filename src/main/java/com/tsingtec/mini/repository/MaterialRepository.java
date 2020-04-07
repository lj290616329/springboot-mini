package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.file.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer>, JpaSpecificationExecutor<Material> {

    @Modifying
    @Transactional
    @Query("delete from Material m where m.id in (?1)")
    void deleteBatch(List<Integer> mids);

}
