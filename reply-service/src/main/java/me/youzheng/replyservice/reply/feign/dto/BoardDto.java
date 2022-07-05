package me.youzheng.replyservice.reply.feign.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "boardNo")
public class BoardDto implements Comparable<BoardDto> {

    private Integer boardNo;

    private String title;

    private String content;

    private Integer userNo;

    private int viewCount;

    private List<ReplyDto> replies = new ArrayList<>();

    @Override
    public int compareTo(BoardDto o) {
        return this.boardNo - o.boardNo;
    }

}