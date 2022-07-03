package me.youzheng.replyservice.reply.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.youzheng.common.domain.MetaData;
import me.youzheng.common.domain.ScrollPage;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import me.youzheng.replyservice.reply.exception.ReplyException;
import me.youzheng.replyservice.reply.mapper.ReplyMapper;
import me.youzheng.replyservice.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final SecurityUtil securityUtil;
    private final ReplyMapper replyMapper = ReplyMapper.INSTANCE;

    @Override
    public ReplyDto create(Reply reply) {
        if (reply == null || ObjectUtils.isEmpty(reply.getBoardNo())
            || !StringUtils.hasLength(reply.getContent())) {
            throw new ReplyException("잘못 입력하셨습니다..");
        }
        reply.setMetaData(new MetaData());
        Reply save = this.replyRepository.save(reply);
        return ReplyDto.from(save);
    }

    @Override
    public void modify(Reply updateReply) {
        Reply reply = this.replyRepository.findById(updateReply.getReplyNo())
            .orElseThrow(() -> new ReplyException("존재하지 않는 댓글입니다.", 404));

        Integer currentUserSeq = securityUtil.getUserPrimaryKey();
        if (!reply.isOwner(currentUserSeq)) {
            throw new ReplyException("권한이 없습니다.", 403);
        }
        this.replyMapper.map(reply, updateReply);
    }

    @Override
    public List<ReplyDto> fetchByBoardNo(Integer boardNo, ScrollPage scrollPage) {
        List<ReplyDto> result = this.replyRepository.findAllByBoardNo(boardNo,
            scrollPage.getFromPk(), scrollPage.getSize());
        return result;
    }

}