package com.zephon.service;

import com.zephon.config.IdWorker;
import com.zephon.dao.CompetitionDao;
import com.zephon.domain.Competition;
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
 * @date 2020/2/29 下午2:21
 * @Copyright ©
 */
@Service
@Transactional
public class CompetitionService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CompetitionDao competitionDao;
    public List<Competition> findAll() {
        return competitionDao.findAll();
    }

    public void save(Competition competition) {
        competition.setId(idWorker.nextId()+"");
        competitionDao.save(competition);
    }

    public void update(Competition competition) {
        Competition c = competitionDao.findById(competition.getId()).get();
        c.setName(competition.getName());
        c.setCreateTime(competition.getCreateTime());
        c.setPrize(competition.getPrize());
        c.setType(competition.getType());
        c.setLevel(competition.getLevel());
        c.setEnrollStart(competition.getEnrollStart());
        c.setEnrollEnd(competition.getEnrollEnd());
        c.setCompStart(competition.getCompStart());
        c.setCompEnd(competition.getCompEnd());
        c.setHost(competition.getHost());
        c.setSummary(competition.getSummary());
        c.setInstitution(competition.getInstitution());
        c.setAudience(competition.getAudience());
        c.setPriceSetting(competition.getPriceSetting());
        c.setContactInformation(competition.getContactInformation());
        c.setCost(competition.getCost());
        competitionDao.save(c);
    }

    public void delete(String id) {
        competitionDao.deleteById(id);
    }

    public Competition findById(String id) {
        return competitionDao.findById(id).get();
    }

    public Page<Competition> findByTypeAndLevelAndSort(Integer page, Integer size, Map<String,Object> map) {
        Specification<Competition> specification = new Specification<Competition>() {
            @Override
            public Predicate toPredicate(Root<Competition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("type"))) {
                    list.add(criteriaBuilder.equal(root.get("type").as(String.class), map.get("type")));
                }
                if (!StringUtils.isEmpty(map.get("level"))) {
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), map.get("level")));
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
        return competitionDao.findAll(specification, PageRequest.of(page-1,size));

    }

//
////        System.out.println(type+"  "+level+"  "+sort);
//        if(level!=null) {
//            int lev = 0;
//            switch (level) {
//                case "校级":
//                    lev = 0;
//                    break;
//                case "市级":
//                    lev = 1;
//                    break;
//                case "省级":
//                    lev = 2;
//                    break;
//                case "国家级":
//                    lev = 3;
//                    break;
//                default:
//                    break;
//            }
//            return competitionDao.findByTypeAndLevelAndSort(type,lev,sort);
//        }else{
//            return competitionDao.findByTypeAndLevelAndSort(type,null,sort);
//        }

//        if(type!=null){
//            if(level!=null){
//                int lev = 0;
//                switch (level){
//                    case "校级":lev=0;break;
//                    case "市级":lev=1;break;
//                    case "省级":lev=2;break;
//                    case "国家级":lev=3;break;
//                    default:break;
//                }
//                if(sort!=null){
//                    return competitionDao.findByTypeAndLevelAndSort(type,lev,sort);
//                }else{
//                    return competitionDao.findByCompTypeAndLevel(type,lev);
//                }
//            }else{
//                System.out.println(type);
//                return competitionDao.findByCompType(type);
//            }
//        }
//
//        System.out.println(competitionDao.findByCompType(type));
//        return null;
//    }
}
