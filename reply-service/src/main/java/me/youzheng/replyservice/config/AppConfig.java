package me.youzheng.replyservice.config;

import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.common.security.util.SecurityUtilImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityUtil securityUtil() {
        return new SecurityUtilImpl();
    }

}