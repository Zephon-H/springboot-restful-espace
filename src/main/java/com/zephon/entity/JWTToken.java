package com.zephon.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.entity
 * @date 2020/2/28 下午6:31
 * @Copyright ©
 */

public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
