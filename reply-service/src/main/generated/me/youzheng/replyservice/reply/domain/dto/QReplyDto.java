package me.youzheng.replyservice.reply.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * me.youzheng.replyservice.reply.domain.dto.QReplyDto is a Querydsl Projection type for ReplyDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReplyDto extends ConstructorExpression<ReplyDto> {

    private static final long serialVersionUID = -1888895954L;

    public QReplyDto(com.querydsl.core.types.Expression<Integer> replyNo, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Integer> boardNo, com.querydsl.core.types.Expression<Integer> userNo, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDateTime) {
        super(ReplyDto.class, new Class<?>[]{int.class, String.class, int.class, int.class, String.class, java.time.LocalDateTime.class}, replyNo, content, boardNo, userNo, userName, createDateTime);
    }

}

