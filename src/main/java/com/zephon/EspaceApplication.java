package com.zephon;

import com.zephon.config.IdWorker;
import com.zephon.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon
 * @date 2020/2/28 下午4:24
 * @Copyright ©
 */
@SpringBootApplication(scanBasePackages = "com.zephon")
@EntityScan(basePackages = "com.zephon.domain")
public class EspaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EspaceApplication.class,args);
    }
    @Bean
    public JwtUtil jwtUtils(){
        return new JwtUtil();
    }

    @Bean
    public IdWorker idWorker(){return new IdWorker();}

    /**
     * 解决jpa+拦截器会造成延迟加载问题
     * 解决no session问题
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }

}
