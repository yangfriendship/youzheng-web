package me.youzheng.common.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;
import me.youzheng.common.security.domain.UserContext;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@EntityListeners(AuditingEntityListener.class)
public class MetaData {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createId;

    private Integer createdNo;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @LastModifiedBy
    private String modifiedId;

    private Integer modifiedNo;

    private boolean deleteYn;

    @PrePersist
    protected void prePersist() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserContext)) {
            this.createId = "SYSTEM";
            this.createdNo = null;
        } else {
            UserContext userContext = (UserContext) principal;
            this.createdNo = userContext.getUserNo();
            this.createId = userContext.getUserEntity().getLoginId();
        }
        this.createDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void PreUpdate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserContext)) {
            this.createId = "SYSTEM";
            this.createdNo = null;
        } else {
            UserContext userContext = (UserContext) principal;
            this.createdNo = userContext.getUserNo();
            this.createId = userContext.getUserEntity().getLoginId();
        }
        this.createDateTime = LocalDateTime.now();
    }

}