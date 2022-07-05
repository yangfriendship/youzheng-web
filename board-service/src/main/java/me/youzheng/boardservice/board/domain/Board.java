package me.youzheng.boardservice.board.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.youzheng.common.domain.MetaData;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNo;

    private String title;

    private String content;

    private Integer userNo;

    private int viewCount;

    @Embedded
    private MetaData metaData;

    public Board() {
        this.metaData = new MetaData();
    }

    public boolean isOwner(Integer userNo) {
        if (this.metaData == null || this.metaData.getCreatedNo() == null) {
            throw new IllegalArgumentException("Board 에 metadata 가 존재하지 않습니다.");
        }
        return this.metaData.getCreatedNo().equals(userNo);
    }

    public boolean canIncrementViewCount() {
        return this.viewCount < 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(
            o)) {
            return false;
        }
        Board board = (Board) o;
        return boardNo != null && Objects.equals(boardNo, board.boardNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}