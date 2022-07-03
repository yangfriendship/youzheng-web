package me.youzheng.replyservice.reply.controller;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.common.domain.ScrollPage;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import me.youzheng.replyservice.reply.service.ReplyService;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ReplyController {

    public static final String URL_PREFIX = "/api/replies";
    private final ReplyService replyService;

    @PostMapping(URL_PREFIX)
    public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto replyDto) {
        log.info("댓글 등록: {}", replyDto);
        ReplyDto result = this.replyService.create(replyDto.to());
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/api/boards/{boardNo}/replies")
    public ResponseEntity<List<ReplyDto>> fetchReplies(@PathVariable Integer boardNo,
        @RequestParam(required = false) ScrollPage scrollPage) {
        // TODO ArgumentMethodResolver
        if (scrollPage == null) {
            scrollPage = new ScrollPage();
        }
        List<ReplyDto> replies = this.replyService.fetchByBoardNo(boardNo, scrollPage);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/api/replies")
    public ResponseEntity<List<ReplyDto>> fetchRepliesAsSlice(
        @ModelAttribute ScrollPage scrollPage) {
        // TODO ArgumentMethodResolver
        if (scrollPage == null) {
            scrollPage = new ScrollPage();
        }
        List<ReplyDto> replies = this.replyService.fetchByBoardNo(scrollPage.getBoardNo(),
            scrollPage);
        return ResponseEntity.ok(replies);
    }

}