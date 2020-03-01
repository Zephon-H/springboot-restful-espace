package com.zephon.dao;

import com.zephon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.dao.system
 * @date 2020/2/28 下午4:07
 * @Copyright ©
 */
public interface UserDao extends JpaRepository<User,String >, JpaSpecificationExecutor<User> {
    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);
}
