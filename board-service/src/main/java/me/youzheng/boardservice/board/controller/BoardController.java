package me.youzheng.boardservice.board.controller;

import lombok.RequiredArgsConstructor;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.boardservice.board.domain.dto.BoardCreateDto;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import me.youzheng.boardservice.board.service.BoardService;
import me.youzheng.boardservice.board.validate.BoardCreateValidator;
import me.youzheng.boardservice.exception.BoardException;
import me.youzheng.common.security.annotation.LoginUser;
import me.youzheng.common.security.domain.UserContext;
import me.youzheng.common.security.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardCreateValidator boardCreateValidator;
    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<BoardDto> create(@LoginUser UserContext userContext, @RequestBody
        BoardCreateDto boardCreateDto, Errors errors) {
        this.boardCreateValidator.validate(boardCreateDto, errors);
        if (errors.hasErrors()) {
            throw new BoardException(errors);
        }
        Board board = boardCreateDto.toBoard();
        board.setBoardNo(userContext.getUserNo());

        Board save = this.boardService.create(board);
        BoardDto result = BoardDto.from(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/api/boards")
    public ResponseEntity<Page<BoardDto>> fetch(@PageableDefault Pageable pageable,
        @RequestParam(required = false) BoardFetchDto boardFetchDto) {
        Page<BoardDto> result = this.boardService.fetch(boardFetchDto, pageable);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/api/boards/{boardNo}")
    public ResponseEntity<?> modify(@PathVariable("boardNo") Integer boardNo, Errors errors,
        @RequestBody BoardDto boardDto, @LoginUser UserEntity userEntity) {
        if (!isOwner(boardNo, userEntity)) {
            throw new BoardException("게시물에 대한 권한이 없습니다.", 403);
        }

        Board board = boardDto.to();
        this.boardCreateValidator.validate(board, errors);
        if (errors.hasErrors()) {
            throw new BoardException(errors);
        }

        this.boardService.modify(board);
        return ResponseEntity.noContent().build();
    }

    private boolean isOwner(Integer boardNo, UserEntity userEntity) {
        return userEntity != null
            && userEntity.getUserNo() != null
            && userEntity.getUserNo().equals(boardNo);
    }

}