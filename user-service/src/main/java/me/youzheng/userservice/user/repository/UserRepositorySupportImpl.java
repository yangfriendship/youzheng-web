package me.youzheng.userservice.user.repository;

import static me.youzheng.userservice.user.domain.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.youzheng.userservice.user.domain.User;
import me.youzheng.userservice.user.domain.dto.UserRequest;
import me.youzheng.userservice.user.domain.dto.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UserRepositorySupportImpl implements UserRepositorySupport {

    private final JPQLQueryFactory query;

    @Override
    public List<UserResponse> findUsers(UserRequest userRequest, Pageable pageable) {
        List<User> users = this.query.selectFrom(user)
            .where(createWhere(userRequest))
            .fetch();
        return null;
    }

    private Predicate createWhere(UserRequest userRequest) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userRequest == null) {
            return builder;
        }
        if (userRequest.getUserNo() != null && userRequest.getUserNo() > 0) {
            builder.and(user.userNo.eq(userRequest.getUserNo()));
        }
        if (StringUtils.hasLength(userRequest.getEmail())) {
            builder.and(user.email.eq(userRequest.getEmail()));
        }
        if (StringUtils.hasLength(userRequest.getUserName())) {
            builder.and(user.userName.eq(userRequest.getUserName()));
        }
        return builder;
    }

}
