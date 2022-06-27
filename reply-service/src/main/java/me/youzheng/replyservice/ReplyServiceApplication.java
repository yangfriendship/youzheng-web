package me.youzheng.replyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReplyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReplyServiceApplication.class, args);
    }

}