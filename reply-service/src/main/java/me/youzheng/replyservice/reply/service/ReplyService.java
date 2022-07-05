package me.youzheng.replyservice.reply.service;

import java.util.List;
import me.youzheng.common.domain.ScrollPage;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReplyService {

    ReplyDto create(Reply reply);

    long modify(Reply reply);

    List<ReplyDto> fetchByBoardNo(Integer boardNo, ScrollPage slice);

    long removeAllByBoardNo(Integer boardNo, Integer userNo);

    long removeByReplyNo(Integer replyNo);

    boolean isOwnerOfReply(Integer replyNo, Integer userNo);

}