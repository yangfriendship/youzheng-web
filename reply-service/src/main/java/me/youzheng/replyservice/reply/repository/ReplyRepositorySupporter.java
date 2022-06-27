package me.youzheng.replyservice.reply.repository;

import java.util.List;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import org.springframework.data.domain.Slice;

public interface ReplyRepositorySupporter {

    public List<ReplyDto> findAllByBoardNo(Integer boardNo, Integer fromNumber, int count);

}