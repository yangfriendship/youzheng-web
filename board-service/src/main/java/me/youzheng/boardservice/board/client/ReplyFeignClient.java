package me.youzheng.boardservice.board.client;

import java.util.List;
import me.youzheng.boardservice.board.domain.dto.ReplyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reply-service", url = "${youzheng.reply-service.url}", fallbackFactory = ReplyFallbackFactory.class)
public interface ReplyFeignClient {

    @GetMapping("/api/boards/{boardNo}")
    List<ReplyDto> fetchRepliesByBoardNo(@PathVariable("boardNo") Integer boardNo);

}