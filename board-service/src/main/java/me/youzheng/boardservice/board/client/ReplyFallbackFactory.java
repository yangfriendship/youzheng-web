package me.youzheng.boardservice.board.client;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.boardservice.board.domain.dto.ReplyDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReplyFallbackFactory implements FallbackFactory<ReplyFeignClient> {

    @Override
    public ReplyFeignClient create(Throwable cause) {
        return new ReplyFeignClient() {
            @Override
            public List<ReplyDto> fetchRepliesByBoardNo(Integer boardNo) {
                log.error("ReplyFeignClient fail boardNo: {}", boardNo);
                return Collections.emptyList();
            }

            @Override
            public Long deleteRepliesByBoardNo(Integer boardNo) {
                log.error("ReplyFeignClient fail boardNo: {}", boardNo);
                return 0L;
            }
        };
    }

}