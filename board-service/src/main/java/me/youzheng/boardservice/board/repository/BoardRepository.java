package me.youzheng.boardservice.board.repository;

import java.util.List;
import me.youzheng.boardservice.board.domain.Board;
import me.youzheng.boardservice.board.domain.dto.BoardFetchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositorySupporter {

}
