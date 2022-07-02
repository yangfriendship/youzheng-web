package me.youzheng.boardservice.board.service;

import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardService {

    Board create(Board board);

    Page<BoardDto> fetchBoards(BoardFetchDto boardFetchDto, Pageable pageable);

    BoardDto fetchBoard(Integer boardNo);

    long fetchBoardCountBy(BoardFetchDto boardFetchDto);

    boolean modify(Board board);

    long deleteBy(Integer boardNo, Integer requestNo);
}