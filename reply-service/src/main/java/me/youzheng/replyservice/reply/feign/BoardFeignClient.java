package me.youzheng.replyservice.reply.feign;

import static me.youzheng.common.constants.UrlConstants.BOARD_SERVICE_URL;

import me.youzheng.common.config.DefaultFeignConfig;
import me.youzheng.replyservice.reply.feign.dto.BoardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "board-service", url = "${youzheng.feign.board-service.url}", path = BOARD_SERVICE_URL,
    configuration = DefaultFeignConfig.class, fallbackFactory = BoardFallbackFactory.class, primary = false)
public interface BoardFeignClient {

    @GetMapping("/{boardNo}")
    BoardDto fetchBoardByBoardNo(@PathVariable Integer boardNo);

}