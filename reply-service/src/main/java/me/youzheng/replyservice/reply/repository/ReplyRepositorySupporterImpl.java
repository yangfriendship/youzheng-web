package me.youzheng.replyservice.reply.repository;


import static me.youzheng.replyservice.reply.domain.QReply.reply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.youzheng.replyservice.reply.domain.dto.QReplyDto;
import me.youzheng.replyservice.reply.domain.dto.ReplyDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyRepositorySupporterImpl implements ReplyRepositorySupporter {

    private final JPAQueryFactory query;

    @Override
    public List<ReplyDto> findAllByBoardNo(Integer boardNo, Integer fromNumber, int count) {
        return query.select(new QReplyDto(
                reply.replyNo,
                reply.content,
                reply.boardNo,
                reply.metaData.createdNo,
                reply.metaData.createId,
                reply.metaData.createDateTime
            )).from(reply)
            .where(this.createWhere(boardNo, fromNumber))
            .limit(count)
            .fetch();
    }

    private BooleanBuilder createWhere(Integer boardNo, Integer fromPk) {
        BooleanBuilder builder = new BooleanBuilder();
        if (boardNo == null || boardNo < 0) {
            return builder;
        }
        builder.and(reply.boardNo.eq(boardNo));

        if (fromPk != null && fromPk > 0) {
            builder.and(reply.replyNo.gt(fromPk));
        }
        builder.and(reply.metaData.deleteYn.isFalse());

        return builder;
    }

}