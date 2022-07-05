package me.youzheng.replyservice.reply.feign;

import lombok.extern.slf4j.Slf4j;
import me.youzheng.replyservice.reply.feign.dto.BoardDto;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class BoardFallbackFactory implements FallbackFactory<BoardFeignClient> {

    @Override
    public BoardFeignClient create(Throwable cause) {
        return new BoardFeignClient() {
            @Override
            public BoardDto fetchBoardByBoardNo(Integer boardNo) {
                log.error("BoardFeignClient Error! boardNo: {}", boardNo);
                return new BoardDto();
            }
        };
    }

}