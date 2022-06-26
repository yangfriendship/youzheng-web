package me.youzheng.common.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class MetaData {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createId;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @LastModifiedBy
    private String modifiedId;

    private boolean deleteYn;

}