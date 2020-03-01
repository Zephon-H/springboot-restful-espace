package com.zephon.service;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zephon.dao.TeamDao;
import com.zephon.dao.UserDao;
import com.zephon.domain.Team;
import com.zephon.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.service
 * @date 2020/2/28 下午3:19
 * @Copyright ©
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TeamDao teamDao;
    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }

    public User findById(String id) {
        return userDao.findById(id).get();
    }

    public Page<User> findAll(Integer page, Integer size) {
        return userDao.findAll(PageRequest.of(page - 1, size));
    }

    public void addTeam(String userId, String teamId) {
        User user = userDao.findById(userId).get();
        Team team = teamDao.findById(teamId).get();
        Set<Team> teams = user.getTeams();
        teams.add(team);
        user.setTeams(teams);
    }

    /**
     * 完成图片处理
     * @param id    :用户id
     * @param file ：用户上传的头像
     * @return      ：请求路径
     */
    public String uploadImage(String id, MultipartFile file) throws IOException {
        // 根据id查询用户
        User user = userDao.findById(id).get();
        // 使用DataURL的形式存储图片(对图片byte数组进行base64编码）
        String encode = "data:image/png;base64,"+ Base64.encode(file.getBytes());
        System.out.println(encode);
        // 更新用户头像地址
        user.setAvatar(encode);
        userDao.save(user);
        // 返回
        return encode;
    }
}
