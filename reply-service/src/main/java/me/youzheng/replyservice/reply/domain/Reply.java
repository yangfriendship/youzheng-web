package me.youzheng.replyservice.reply.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.youzheng.common.domain.MetaData;
import org.hibernate.Hibernate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reply",
    indexes = {
        @Index(name = "index_board_no_reply_no",
            columnList = "boardNo, replyNo"
        )
    }
)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replyNo;

    private String content;

    private Integer boardNo;

    @Embedded
    private MetaData metaData;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(
            o)) {
            return false;
        }
        Reply reply = (Reply) o;
        return replyNo != null && Objects.equals(replyNo, reply.replyNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean isOwner(Integer userNo) {
        return userNo != null && this.metaData != null && userNo.equals(
            this.metaData.getCreatedNo());
    }

}