package me.youzheng.replyservice.reply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.replyservice.reply.domain.Reply;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import me.youzheng.replyservice.reply.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ReplyController {

    public static final String URL_PREFIX = "/api/replies";
    private final ReplyService replyService;

    @GetMapping(URL_PREFIX)
    public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto replyDto) {
        log.info("댓글 등록: {}", replyDto);
        Reply reply = this.replyService.create(replyDto.to());
        return ResponseEntity.status(201).body(null);
    }

}