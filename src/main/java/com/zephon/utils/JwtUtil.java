package com.zephon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.utils
 * @date 2020/2/28 下午5:02
 * @Copyright ©
 */
@Getter
@Setter
@ConfigurationProperties("jwt.config")
public class JwtUtil {
    /**
     * 签名私钥
     */
    private String key;
    /**
     * 签名失效时间
     */
    private Long ttl;
    /**
     * 设置认证token
     *  id:登录用户id
     *  subject：登录用户名
     */
    public String createJwt(String id, String name, Map<String ,Object> map){
        // 1.设置失效时间
        long now = System.currentTimeMillis();
        long exp = now + ttl;
        // 2.创建jwtBuilder
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        // 3.根据map设置claims
        for(Map.Entry<String,Object> entry:map.entrySet()){
            builder.claim(entry.getKey(),entry.getValue());
        }
        // 设置失效时间
        builder.setExpiration(new Date(exp));
        // 4.创建token
        String token = builder.compact();
        return token;

    }
    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

}
