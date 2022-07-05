package me.youzheng.replyservice.reply.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.youzheng.common.domain.MetaData;
import me.youzheng.common.domain.ScrollPage;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import me.youzheng.replyservice.reply.exception.ReplyException;
import me.youzheng.replyservice.reply.feign.BoardFeignClient;
import me.youzheng.replyservice.reply.feign.dto.BoardDto;
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
    private final BoardFeignClient boardFeignClient;

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
    public long modify(Reply updateReply) {
        Reply reply = this.replyRepository.findById(updateReply.getReplyNo())
            .orElseThrow(() -> new ReplyException("존재하지 않는 댓글입니다.", 404));

        Integer currentUserSeq = securityUtil.getUserPrimaryKey();
        if (!reply.isOwner(currentUserSeq)) {
            throw new ReplyException("권한이 없습니다.", 403);
        }
        this.replyMapper.map(reply, updateReply);
        // TODO return 0 말고 다른걸 반환하도록 변경
        return 0;
    }

    @Override
    public List<ReplyDto> fetchByBoardNo(Integer boardNo, ScrollPage scrollPage) {
        List<ReplyDto> result = this.replyRepository.findAllByBoardNo(boardNo,
            scrollPage.getFromPk(), scrollPage.getSize());
        return result;
    }

    @Override
    public long removeAllByBoardNo(Integer boardNo, Integer userNo) {
        BoardDto boardDto = this.boardFeignClient.fetchBoardByBoardNo(boardNo);
        if (boardDto.getUserNo() == null || !boardDto.getUserNo().equals(userNo)) {
            throw new ReplyException("권한이 없습니다.", 403);
        }
        return this.replyRepository.updateDeleteYnByBoardNo(boardNo, true);
    }

    @Override
    public long removeByReplyNo(Integer replyNo) {
        return 0;
    }

    @Override
    public boolean isOwnerOfReply(Integer replyNo, Integer userNo) {
        return this.replyRepository.existsByReplyNoAndUserNo(replyNo, userNo);
    }

}