package me.youzheng.boardservice.config;

import me.youzheng.boardservice.board.validate.BoardCreateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public BoardCreateValidator boardCreateValidator() {
        return new BoardCreateValidator();
    }

}