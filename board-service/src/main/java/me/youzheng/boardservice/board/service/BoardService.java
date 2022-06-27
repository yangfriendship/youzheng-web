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

    Page<BoardDto> fetch(BoardFetchDto boardFetchDto, Pageable pageable);

    boolean modify(Board board);

}