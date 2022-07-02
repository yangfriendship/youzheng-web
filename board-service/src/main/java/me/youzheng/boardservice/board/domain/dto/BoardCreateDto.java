package me.youzheng.boardservice.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.common.domain.MetaData;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BoardCreateDto {

    private String title;

    private String content;

    private Integer userNo;

    public Board toBoard() {
        return Board.builder()
            .viewCount(0)   // default 0
            .title(this.title)
            .content(this.content)
            .metaData(new MetaData())
            .build();
    }

}