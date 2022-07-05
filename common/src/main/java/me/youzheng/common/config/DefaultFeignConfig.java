package me.youzheng.common.config;

import feign.Logger;
import feign.Logger.Level;
import feign.RequestInterceptor;
import me.youzheng.common.feign.TokenHeaderInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @Configuration 사용하지 않고 @FeignClient(configuration = ${className}) 직접 명시하여 사용한다.
 */
public class DefaultFeignConfig {

    @Bean
    public RequestInterceptor tokenHeaderInterceptor() {
        return new TokenHeaderInterceptor();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Level.FULL;
    }

}