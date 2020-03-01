package com.zephon;

import com.zephon.dao.CompetitionDao;
import com.zephon.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon
 * @date 2020/2/29 下午12:26
 * @Copyright ©
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EspaceApplication.class})
public class UserTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompetitionDao competitionDao;

    @Transactional
    @Test
    public void testQuery(){
        String str = "数学建模";
//        List<Competition> c = competitionDao.findByTypeAndLevelAndSort(str,null,null);
//        System.out.println(c);

//        User user = userDao.findByMobile("17683782032");
//        Set<Role> roles = user.getRoles();
//        System.out.println(roles);

    }
}
