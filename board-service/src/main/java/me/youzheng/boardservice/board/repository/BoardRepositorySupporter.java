package me.youzheng.boardservice.board.repository;

import java.util.List;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import org.springframework.data.domain.Pageable;

public interface BoardRepositorySupporter {

    List<BoardDto> findAllBy(BoardFetchDto boardFetchDto, Pageable pageable);

    long countBy(BoardFetchDto boardFetchDto);

    long updateDeleteYn(Integer boardNo, boolean state);
}