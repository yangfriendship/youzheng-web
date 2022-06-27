package me.youzheng.replyservice.reply.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.youzheng.replyservice.reply.domain.Reply;

@Data
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "replyNo")
public class ReplyDto {

    private Integer replyNo;

    private String content;

    private Integer boardNo;

    private Integer userNo;

    private String userName;

    private LocalDateTime createDateTime;

    @QueryProjection
    public ReplyDto(Integer replyNo, String content, Integer boardNo, Integer userNo,
        String userName, LocalDateTime createDateTime) {
        this.replyNo = replyNo;
        this.content = content;
        this.boardNo = boardNo;
        this.userNo = userNo;
        this.userName = userName;
        this.createDateTime = createDateTime;
    }

    public Reply to() {
        return Reply.builder()
            .boardNo(this.boardNo)
            .content(this.content)
            .build();
    }

}