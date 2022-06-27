package me.youzheng.userservice.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1715035939L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.time.LocalDateTime> createDatetime = createDateTime("createDatetime", java.time.LocalDateTime.class);

    public final StringPath createId = createString("createId");

    public final StringPath email = createString("email");

    public final BooleanPath lockYn = createBoolean("lockYn");

    public final StringPath loginId = createString("loginId");

    public final StringPath password = createString("password");

    public final EnumPath<me.youzheng.common.security.domain.Role> role = createEnum("role", me.youzheng.common.security.domain.Role.class);

    public final StringPath userName = createString("userName");

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

