package me.youzheng.boardservice.board.client;

import java.util.List;
import me.youzheng.boardservice.board.domain.dto.ReplyDto;
import me.youzheng.boardservice.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "reply-service", url = "${youzheng.feign.reply-service.url}",
    configuration = DefaultFeignConfig.class, fallbackFactory = ReplyFallbackFactory.class)
public interface ReplyFeignClient {

    @GetMapping("/api/boards/{boardNo}/replies")
    List<ReplyDto> fetchRepliesByBoardNo(@PathVariable("boardNo") Integer boardNo);

    @DeleteMapping("/api/boards/{boardNo}/replies")
    Long deleteRepliesByBoardNo(@PathVariable("boardNo") Integer boardNo);

}