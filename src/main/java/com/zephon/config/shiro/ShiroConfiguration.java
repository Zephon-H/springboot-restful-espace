package com.zephon.config.shiro;

import com.zephon.filter.JwtFilter;
import com.zephon.realm.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.config.shiro
 * @date 2020/2/28 下午2:42
 * @Copyright ©
 */
@Configuration
public class ShiroConfiguration {
    /**
     * 创建realm
     * @return
     */
    @Bean
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 创建安全管理器
     * @param userRealm
     * @return
     */
    @Bean
    public SecurityManager getSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

//        // 将自定义的会话管理器注册到安全管理器中
//        securityManager.setSessionManager(sessionManager());
//        // 将自定义的redis缓存管理器注册到安全管理器中
//        securityManager.setCacheManager(cacheManager());
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 配置shiro过滤器工厂
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        // 1. 创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        // 2. 设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        // 3. 通用配置(跳转登录页面，未授权页面)
        filterFactory.setLoginUrl("/autherror?code=1");
        filterFactory.setUnauthorizedUrl("/autherror?code=2");
        // 4. 设置过滤集合
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt",new JwtFilter());
        filterFactory.setFilters(filterMap);

        Map<String,String > filterRuleMap = new LinkedHashMap<>();
        // anon -- 匿名访问
        filterRuleMap.put("/sys/login","anon");
        filterRuleMap.put("/autherror","anon");
        filterRuleMap.put("/sys/faceLogin/**","anon");
        // 自定义过滤器
        filterRuleMap.put("/**","jwt");
        // perms -- 具有某种权限 (使用注解)

        filterFactory.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactory;
    }

//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;
//    /**
//     * redis控制器
//     * @return
//     */
//    @Bean
//    public RedisManager redisManager(){
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        return redisManager;
//    }
//
//    /**
//     * sessionDao
//     * @return
//     */
//    public RedisSessionDAO redisSessionDAO(){
//        RedisSessionDAO sessionDAO = new RedisSessionDAO();
//        sessionDAO.setRedisManager(redisManager());
//        return sessionDAO;
//    }
//
//    /**
//     * 会话管理器
//     * @return
//     */
//    public DefaultWebSessionManager sessionManager(){
//        CustomSessionManager sessionManager = new CustomSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        // 禁用cookie
//        sessionManager.setSessionIdCookieEnabled(false);
//        // 禁用url重写
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
//        return sessionManager;
//    }
//
//    /**
//     * 缓存管理器
//     * @return
//     */
//    public RedisCacheManager cacheManager(){
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }




}

