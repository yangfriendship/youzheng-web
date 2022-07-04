package me.youzheng.boardservice.board.controller;

import static me.youzheng.common.constants.UrlConstants.*;

import lombok.RequiredArgsConstructor;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.boardservice.board.domain.dto.BoardCreateDto;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import me.youzheng.boardservice.board.service.BoardService;
import me.youzheng.boardservice.board.validate.BoardCreateValidator;
import me.youzheng.boardservice.exception.BoardException;
import me.youzheng.common.constants.UrlConstants;
import me.youzheng.common.security.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    public static final String URL_PREFIX = BOARD_SERVICE_URL;

    private final BoardCreateValidator boardCreateValidator;
    private final BoardService boardService;
    private final SecurityUtil securityUtil;

    @InitBinder("boardCreateDto")
    public void initBoardCreateBinding(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(boardCreateValidator);
    }

    @PostMapping(URL_PREFIX)
    public ResponseEntity<BoardDto> create(@RequestBody BoardCreateDto boardCreateDto,
        Errors errors) {
        if (errors.hasErrors()) {
            throw new BoardException(errors);
        }
        Board board = boardCreateDto.toBoard();
        board.setUserNo(this.securityUtil.getUserPrimaryKey());

        Board save = this.boardService.create(board);
        BoardDto result = BoardDto.from(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(URL_PREFIX + "/{boardNo}")
    public ResponseEntity<BoardDto> fetchBoard(@PathVariable("boardNo") Integer boardNo) {
        BoardDto boardDto = this.boardService.fetchBoard(boardNo);
        return ResponseEntity.ok(boardDto);
    }


    @GetMapping(URL_PREFIX)
    public ResponseEntity<Page<BoardDto>> fetchBoards(@PageableDefault Pageable pageable,
        @ModelAttribute BoardFetchDto boardFetchDto) {
        Page<BoardDto> result = this.boardService.fetchBoards(boardFetchDto, pageable);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(URL_PREFIX + "/{boardNo}")
    public ResponseEntity<?> modify(@PathVariable("boardNo") Integer boardNo, Errors errors,
        @RequestBody BoardDto boardDto) {
        Board board = boardDto.to();
        if (errors.hasErrors()) {
            throw new BoardException(errors);
        }

        this.boardService.modify(board);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(URL_PREFIX + "/{boardNo}")
    public ResponseEntity<?> delete(@PathVariable("boardNo") Integer boardNo) {
        this.boardService.deleteBy(boardNo, this.securityUtil.getUserPrimaryKey());
        return ResponseEntity.noContent().build();
    }

}