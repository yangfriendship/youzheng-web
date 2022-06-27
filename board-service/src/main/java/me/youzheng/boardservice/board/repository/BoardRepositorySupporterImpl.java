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
            .fetch();
        return result;
    }

    private Predicate createWhere(BoardFetchDto boardFetchDto) {
        BooleanBuilder builder = new BooleanBuilder();
        // TODO full text match 생성하면 추가하자
        return builder;
    }

}