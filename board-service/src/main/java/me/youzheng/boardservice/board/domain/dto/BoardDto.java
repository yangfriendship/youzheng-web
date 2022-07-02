package me.youzheng.boardservice.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.common.domain.MetaData;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Integer boardNo;

    private String title;

    private String content;

    private Integer userNo;

    private int viewCount;

    private List<ReplyDto> replies = new ArrayList<>();

    @JsonUnwrapped
    private MetaData metaData;

    public static BoardDto from(Board board) {
        return BoardDto.builder()
            .boardNo(board.getBoardNo())
            .title(board.getTitle())
            .content(board.getContent())
            .userNo(board.getUserNo())
            .viewCount(board.getViewCount())
            .metaData(board.getMetaData())
            .build();
    }

    public Board to() {
        Board board = new Board();
        board.setBoardNo(this.boardNo);
        board.setContent(this.content);
        board.setTitle(this.title);
        return board;
    }

    @QueryProjection
    public BoardDto(Integer boardNo, String title, String content, Integer userNo, int viewCount,
        MetaData metaData) {
        this.boardNo = boardNo;
        this.title = title;
        this.content = content;
        this.userNo = userNo;
        this.viewCount = viewCount;
        this.metaData = metaData;
    }

}