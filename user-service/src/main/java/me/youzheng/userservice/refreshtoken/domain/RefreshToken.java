package me.youzheng.userservice.refreshtoken.domain;


import ch.qos.logback.core.joran.spi.NoAutoStart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.youzheng.userservice.user.domain.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
    name = "refresh_token",
    indexes =
        {
            @Index(name = "refresh_user_no_index", columnList = "user_no")
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenNo;

    @JoinColumn(name = "user_no")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)   // inner join
    private User user;

    @Column(nullable = false)
    private String refreshToken;

}