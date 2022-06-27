package me.youzheng.boardservice.board.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * me.youzheng.boardservice.board.domain.dto.QBoardDto is a Querydsl Projection type for BoardDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardDto extends ConstructorExpression<BoardDto> {

    private static final long serialVersionUID = -1461049294L;

    public QBoardDto(com.querydsl.core.types.Expression<Integer> boardNo, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Integer> userNo, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<? extends me.youzheng.common.domain.MetaData> metaData) {
        super(BoardDto.class, new Class<?>[]{int.class, String.class, String.class, int.class, int.class, me.youzheng.common.domain.MetaData.class}, boardNo, title, content, userNo, viewCount, metaData);
    }

}

