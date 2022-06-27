package me.youzheng.replyservice.reply.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCreateDto {

    private Integer boardNo;

    private String content;

}