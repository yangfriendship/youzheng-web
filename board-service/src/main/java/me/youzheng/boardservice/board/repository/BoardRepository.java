package me.youzheng.boardservice.board.repository;

import java.util.Optional;
import me.youzheng.boardservice.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardRepositorySupporter {

    @Query("select b from Board b where b.boardNo = :boardNo and b.metaData.deleteYn = false")
    Optional<Board> findById(Integer boardNo);

}