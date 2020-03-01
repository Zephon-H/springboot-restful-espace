package com.zephon.dao;

import com.zephon.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.dao.system
 * @date 2020/2/29 下午2:20
 * @Copyright ©
 */
public interface CompetitionDao extends JpaRepository<Competition,String>, JpaSpecificationExecutor<Competition> {
//    @Query(value = "select * from tb_competition where ",nativeQuery = true)
//    List<Competition> findByTypeAndLevelAndSort(String type, Integer lev, String sort);
}
