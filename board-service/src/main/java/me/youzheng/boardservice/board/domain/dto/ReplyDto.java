package me.youzheng.boardservice.board.domain.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReplyDto {

    private Integer replyNo;

    private String content;

    private Integer boardNo;

    private Integer userNo;

    private String userName;

    private LocalDateTime createDateTime;

}