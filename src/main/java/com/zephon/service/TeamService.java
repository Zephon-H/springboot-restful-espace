package com.zephon.service;

import com.zephon.config.IdWorker;
import com.zephon.dao.TeamDao;
import com.zephon.dao.UserDao;
import com.zephon.domain.Project;
import com.zephon.domain.Team;
import com.zephon.domain.User;
import com.zephon.domain.request.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.service
 * @date 2020/2/29 下午7:34
 * @Copyright ©
 */
@Service
@Transactional
public class TeamService {
    @Autowired
    private TeamDao teamDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdWorker idWorker;
    public List<Team> findAll() {
        return teamDao.findAll();
    }

    public Team findById(String id) {
        return teamDao.findById(id).get();
    }

    public void save(TeamInfo teamInfo) {
        Team team = new Team();
        team.setId(idWorker.nextId()+"");
        team.setName(teamInfo.getName());
        team.setCreateTime(new Date());
        team.setNum(teamInfo.getTeacherIds().size());
        team.setIntroduction(teamInfo.getIntroduction());
        Set<User> users = new HashSet<>();
        for (String teammateId : teamInfo.getTeammateIds()) {
            users.add(userDao.findById(teammateId).get());
        }
        for (String teacherId : teamInfo.getTeacherIds()) {
            users.add(userDao.findById(teacherId).get());
        }
        team.setUsers(users);
        System.out.println(users);
        teamDao.save(team);
    }

    public void delete(String id) {
        teamDao.deleteById(id);
    }

    public void update(String id, TeamInfo teamInfo) {
        Team team = teamDao.findById(id).get();
        team.setName(teamInfo.getName());
        team.setNum(teamInfo.getTeacherIds().size());
        team.setIntroduction(teamInfo.getIntroduction());
        Set<User> users = new HashSet<>();
        for (String teammateId : teamInfo.getTeammateIds()) {
            users.add(userDao.findById(teammateId).get());
        }
        for (String teacherId : teamInfo.getTeacherIds()) {
            users.add(userDao.findById(teacherId).get());
        }
        team.setUsers(users);
        System.out.println(users);
        teamDao.save(team);
    }

    public Page<Team> findByTypeAndSort(Integer page, Integer size, Map<String, Object> map) {
        Specification<Team> specification = new Specification<Team>() {
            @Override
            public Predicate toPredicate(Root<Team> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
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
        return teamDao.findAll(specification, PageRequest.of(page-1,size));
    }
}
