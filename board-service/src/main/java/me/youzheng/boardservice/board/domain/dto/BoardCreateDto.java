package me.youzheng.boardservice.board.domain.dto;

import lombok.Data;
import me.youzheng.boardservice.board.domain.Board;

@Data
public class BoardCreateDto {

    private String title;

    private String content;

    private Integer userNo;

    public Board toBoard() {
        return Board.builder()
            .viewCount(0)
            .title(this.title)
            .content(this.content)
            .build();
    }

}