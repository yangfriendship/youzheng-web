package me.youzheng.replyservice.reply.controller;

import static me.youzheng.common.constants.UrlConstants.BOARD_SERVICE_URL;
import static me.youzheng.common.constants.UrlConstants.REPLY_SERVICE_URL;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.common.domain.ScrollPage;
import me.youzheng.common.security.util.SecurityUtil;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import me.youzheng.replyservice.reply.exception.ReplyException;
import me.youzheng.replyservice.reply.service.ReplyService;
import me.youzheng.replyservice.reply.validatior.ReplyDtoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ReplyController {

    public static final String URL_PREFIX = REPLY_SERVICE_URL;
    public static final String BOARD_URL_PREFIX = BOARD_SERVICE_URL;
    private final ReplyService replyService;
    private final SecurityUtil securityUtil;

    @InitBinder("replyDto")
    public void replyDtoInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ReplyDtoValidator());
    }

    @PostMapping(URL_PREFIX)
    public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto replyDto, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Reply Create Fail, {}: ", replyDto);
        }

        log.info("댓글 등록: {}", replyDto);
        ReplyDto result = this.replyService.create(replyDto.to());
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping(BOARD_URL_PREFIX + "/{boardNo}/replies")
    public ResponseEntity<List<ReplyDto>> fetchReplies(@PathVariable Integer boardNo,
        @RequestParam(required = false) ScrollPage scrollPage) {
        // TODO ArgumentMethodResolver
        if (scrollPage == null) {
            scrollPage = new ScrollPage();
        }
        List<ReplyDto> replies = this.replyService.fetchByBoardNo(boardNo, scrollPage);
        return ResponseEntity.ok(replies);
    }

    @GetMapping(URL_PREFIX)
    public ResponseEntity<List<ReplyDto>> fetchRepliesAsSlice(
        @ModelAttribute ScrollPage scrollPage) {
        if (scrollPage == null) {
            scrollPage = new ScrollPage();
        }
        List<ReplyDto> replies = this.replyService.fetchByBoardNo(scrollPage.getBoardNo(),
            scrollPage);
        return ResponseEntity.ok(replies);
    }

    @DeleteMapping(URL_PREFIX + "/{replyNo}")
    public ResponseEntity<?> removeOneByBoardNo(@PathVariable("replyNo") Integer replyNo) {
        Integer currentUserNo = this.securityUtil.getUserPrimaryKey();
        if (currentUserNo == null) {
            throw new ReplyException("권한이 없습니다.", 403);
        }

        if (!this.replyService.isOwnerOfReply(replyNo, currentUserNo)) {
            throw new ReplyException("권한이 없습니다.", 403);
        }

        this.replyService.removeByReplyNo(replyNo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(BOARD_URL_PREFIX + "/{boardNo}/replies")
    public ResponseEntity<?> removeAllByBoardNo(@PathVariable Integer boardNo) {
        this.replyService.removeAllByBoardNo(boardNo, securityUtil.getUserPrimaryKey());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(URL_PREFIX + "/{replyNo}")
    public ResponseEntity<?> modifyReply(@PathVariable Integer replyNo, @RequestBody ReplyDto replyDto, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Reply Modify Fail, {}: ", replyDto);
        }
        replyDto.setReplyNo(replyNo);

        this.replyService.modify(replyDto.to());
        return ResponseEntity.noContent().build();
    }

}