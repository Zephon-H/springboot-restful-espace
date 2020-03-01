package com.zephon.dao;

import com.zephon.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.dao
 * @date 2020/2/29 下午7:35
 * @Copyright ©
 */
public interface TeamDao extends JpaRepository<Team,String>, JpaSpecificationExecutor<Team> {
}
