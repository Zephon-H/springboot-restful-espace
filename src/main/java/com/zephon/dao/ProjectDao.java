package com.zephon.dao;

import com.zephon.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.dao
 * @date 2020/2/29 下午6:09
 * @Copyright ©
 */
public interface ProjectDao extends JpaRepository<Project,String >, JpaSpecificationExecutor<Project> {
    List<Project> findByTeacherIdIn(List<String> teacherIds);
}
