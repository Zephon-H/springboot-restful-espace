package com.zephon.dao;

import com.zephon.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.dao.system
 * @date 2020/2/29 上午11:17
 * @Copyright ©
 */
public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {
}
