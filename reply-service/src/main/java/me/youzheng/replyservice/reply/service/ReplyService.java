package me.youzheng.replyservice.reply.service;

import java.util.List;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReplyService {

    Reply create(Reply reply);

    void modify(Reply reply);

    List<ReplyDto> fetchByBoardNo(Integer boardNo, Slice<Reply> slice);
}
