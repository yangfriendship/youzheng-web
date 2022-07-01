package me.youzheng.boardservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import me.youzheng.common.jackson.LocalDateTimeDeserializer;
import me.youzheng.common.jackson.LocalDateTimeSerializer;
import me.youzheng.common.jackson.SimplePageSerializer;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.common.security.util.SecurityUtilImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;

@Configuration
public class AppConfig {

    @Bean
    public SecurityUtil securityUtil() {
        return new SecurityUtilImpl();
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Page.class, new SimplePageSerializer());

        objectMapper.registerModule(simpleModule);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

}