package me.youzheng.boardservice.board.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.youzheng.boardservice.board.client.ReplyFeignClient;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.boardservice.board.domain.dto.BoardDto;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import me.youzheng.boardservice.board.domain.dto.NullBoard;
import me.youzheng.boardservice.board.domain.dto.ReplyDto;
import me.youzheng.boardservice.board.mapper.BoardMapper;
import me.youzheng.boardservice.board.repository.BoardRepository;
import me.youzheng.boardservice.exception.BoardException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyFeignClient replyFeignClient;
    private final BoardMapper boardMapper = BoardMapper.INSTANCE;

    @Override
    public Board create(Board board) {
        if (!isValid(board)) {
            throw new BoardException("올바른 값을 입력해주세요.");
        }
        return this.boardRepository.save(board);
    }

    private boolean isValid(Board board) {
        return board != null
            && StringUtils.hasLength(board.getTitle())
            && StringUtils.hasLength(board.getContent());
    }

    @Override
    public Page<BoardDto> fetchBoards(BoardFetchDto boardFetchDto, Pageable pageable) {
        return Single.zip(
                Single.just(this.boardRepository.findAllBy(boardFetchDto, pageable)),
                Single.just(this.fetchBoardCountBy(boardFetchDto)),
                (boardList, total) -> new PageImpl<>(boardList, pageable, total)
            )
            .blockingGet();
    }

    @Override
    public BoardDto fetchBoard(Integer boardNo) {
        Single<List<ReplyDto>> repliesObservable = Single.just(boardNo)
            .map(this.replyFeignClient::fetchRepliesByBoardNo)
            .observeOn(Schedulers.io())
            .retry(1)
            .onErrorReturn(throwable -> {
                log.error(throwable.getMessage());
                return Collections.emptyList();
            });

        Single<Board> boardObservable = Single.just(boardNo)
            .map(number -> this.boardRepository.findById(number).orElse(NullBoard.getInstance()))
            .observeOn(Schedulers.io())
            .retry(1);

        return Single.zip(boardObservable, repliesObservable,
            (board, replies) -> {
                BoardDto result = BoardDto.from(board);
                result.setReplies(replies);
                return result;
            }
        ).blockingGet();
    }

    @Override
    public long fetchBoardCountBy(BoardFetchDto boardFetchDto) {
        return this.boardRepository.countBy(boardFetchDto);
    }

    @Override
    public boolean modify(Board update) {
        Board board = this.boardRepository.findById(update.getBoardNo())
            .orElseThrow(() -> new BoardException("존재하지 않는 게시물입니다.", NOT_FOUND));
        this.boardMapper.map(update, board);
        return true;
    }

    @Override
    public long deleteBy(Integer boardNo, Integer requestUserNo) {
        Board board = this.boardRepository.findById(boardNo)
            .orElseThrow(() -> new BoardException("존재하지 않는 게시물입니다."));
        if (board.getUserNo() == null || !board.getBoardNo().equals(requestUserNo)) {
            throw new BoardException("작성자만 삭제할 수 있습니다.", 403);
        }

        return Single.zip(
            Single.just(this.replyFeignClient.deleteRepliesByBoardNo(boardNo)),
            Single.just(this.boardRepository.updateDeleteYn(boardNo, false)),
            Long::sum
        ).blockingGet();
    }

}