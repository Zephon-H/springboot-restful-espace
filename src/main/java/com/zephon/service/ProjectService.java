package com.zephon.service;

import com.zephon.config.IdWorker;
import com.zephon.dao.ProjectDao;
import com.zephon.domain.Competition;
import com.zephon.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.service
 * @date 2020/2/29 下午6:10
 * @Copyright ©
 */
@Service
@Transactional
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private IdWorker idWorker;

    public List<Project> findAll() {
        return projectDao.findAll();
    }

    public Project findById(String id) {
        return projectDao.findById(id).get();
    }

    public void update(Project project) {
        Project p = projectDao.findById(project.getId()).get();
        p.setName(project.getName());
        p.setCreateTime(project.getCreateTime());
        p.setDeadline(project.getDeadline());
        p.setType(project.getType());
        p.setEnrollStart(project.getEnrollStart());
        p.setEnrollEnd(project.getEnrollEnd());
        p.setSummary(project.getSummary());
        p.setRequirement(project.getRequirement());
        p.setContact(project.getContact());
        projectDao.save(p);
    }

    public void delete(String id) {
        projectDao.deleteById(id);
    }

    public Page<Project> findByTypeAndSort(Integer page,Integer size,Map<String, Object> map) {
        Specification<Project> specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("type"))) {
                    list.add(criteriaBuilder.equal(root.get("type").as(String.class), map.get("type")));
                }
                if (!StringUtils.isEmpty(map.get("sort"))) {
                    if("asc".equals(map.get("sort"))){
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get((String) map.get("sort"))), criteriaBuilder.asc(root.get((String) map.get("sort"))));
                    }else{
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get((String) map.get("sort"))), criteriaBuilder.desc(root.get((String) map.get("sort"))));
                    }
                    criteriaBuilder.and(criteriaQuery.getRestriction());
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return projectDao.findAll(specification, PageRequest.of(page-1,size));
    }

    public void save(Project project) {
        project.setId(idWorker.nextId()+"");
        projectDao.save(project);
    }

    public List<Project> findByTeacherIds(List<String> teacherIds) {
        return projectDao.findByTeacherIdIn(teacherIds);
    }
}
