package me.youzheng.boardservice.board.service;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import me.youzheng.boardservice.board.repository.BoardRepository;
import me.youzheng.boardservice.exception.BoardException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Board create(Board board) {
        if (!isValid(board)) {
            throw new BoardException("올바른 값을 입력해주세요.");
        }
        return this.boardRepository.save(board);
    }

    private boolean isValid(Board board) {
        return board != null
            && board.getBoardNo() != null
            && StringUtils.hasLength(board.getTitle())
            && StringUtils.hasLength(board.getContent());
    }

    @Override
    public Page<BoardDto> fetch(BoardFetchDto boardFetchDto, Pageable pageable) {
        List<BoardDto> boards = this.boardRepository.findAllBy(boardFetchDto, pageable);
        Page<BoardDto> result = new PageImpl<>(boards, pageable, 0);
        return result;
    }

    @Override
    public boolean modify(Board update) {
        Board board = this.boardRepository.findById(update.getBoardNo())
            .orElseThrow(() -> new BoardException("존재하지 않는 게시물입니다.", NOT_FOUND));
        this.boardMap(update, board);
        return false;
    }

    private void boardMap(Board target, Board source) {
        // TODO null check 해서 dirty check 업데이트 되도록 한다.
    }

}