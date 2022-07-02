package me.youzheng.boardservice.board.repository;


import static me.youzheng.boardservice.board.domain.QBoard.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class BoardRepositorySupporterImpl implements BoardRepositorySupporter {

    private final JPAQueryFactory query;

    @Override
    public List<BoardDto> findAllBy(BoardFetchDto boardFetchDto, Pageable pageable) {
        List<BoardDto> result = this.query
            .select(Projections.constructor(BoardDto.class,
                board.boardNo,
                board.title,
                board.content,
                board.userNo,
                board.viewCount,
                board.metaData
            ))
            .from(board)
            .where(this.createWhere(boardFetchDto))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(board.boardNo.desc())
            .fetch();
        return result;
    }

    @Override
    public long countBy(BoardFetchDto boardFetchDto) {
        return this.query
            .select(board.boardNo.count())
            .from(board)
            .where(this.createWhere(boardFetchDto))
            .fetch().get(0);
    }

    @Override
    public long updateDeleteYn(Integer boardNo, boolean state) {
        return this.query
            .update(board)
            .set(board.metaData.deleteYn, state)
            .execute();
    }

    private Predicate createWhere(BoardFetchDto boardFetchDto) {
        BooleanBuilder builder = new BooleanBuilder();
        // TODO full text match 생성하면 추가하자
        if (boardFetchDto == null) {
            return builder;
        }
        if (StringUtils.hasLength(boardFetchDto.getTitle())) {
            builder.and(board.title.like(boardFetchDto.getTitleForLike()));
        }
        if (boardFetchDto.getMetaData() == null) {
            return builder;
        }
        if (StringUtils.hasLength(boardFetchDto.getMetaData().getCreateId())) {
            builder.and(board.content.like(boardFetchDto.getMetaData().getCreateId()));
        }
        return builder;
    }

}