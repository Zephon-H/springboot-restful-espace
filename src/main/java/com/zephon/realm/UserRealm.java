package com.zephon.realm;

import com.zephon.domain.User;
import com.zephon.domain.response.ProfileResult;
import com.zephon.entity.JWTToken;
import com.zephon.service.UserService;
import com.zephon.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.realm
 * @date 2020/2/28 下午2:44
 * @Copyright ©
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void setName(String name) {
        super.setName("userRealm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据
        ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();
        //2.获取权限信息
        Set<String> roles = result.getRoles();
        //3.构造权限数据，返回值
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(apisPerms);
        info.setRoles(roles);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        Claims claims = jwtUtil.parseJwt(token.replace("Bearer ",""));
        String mobile = (String) claims.get("mobile");
        String password = (String) claims.get("password");

        User user = userService.findByMobile(mobile);
        ProfileResult result = new ProfileResult(user);
        if(user.getPassword().equals(password)){
            return new SimpleAuthenticationInfo(result, token, user.getUsername());
        }
        return null;

//
//
//        JWTToken jwtToken = (JWTToken) authenticationToken;
//        String token = jwtToken.getToken();
//
//        Claims claims = jwtUtil.parseJwt(token);
//        String mobile = (String) claims.get("mobile");
//        String password = (String) claims.get("password");
//        User user = userService.findByMobile(mobile);
//        ProfileResult profileResult = new ProfileResult(user);
//        if(user!=null&&user.getPassword().equals(password)){
//            // 构造方法：安全数据、密码、realm域名
//            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token,user.getPassword(),user.getUsername());
//            return info;
//        }
//        return null;
    }

}
