package me.youzheng.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractBaseEntity is a Querydsl query type for AbstractBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractBaseEntity extends EntityPathBase<AbstractBaseEntity> {

    private static final long serialVersionUID = 91379300L;

    public static final QAbstractBaseEntity abstractBaseEntity = new QAbstractBaseEntity("abstractBaseEntity");

    public final DateTimePath<java.time.LocalDateTime> createDateTime = createDateTime("createDateTime", java.time.LocalDateTime.class);

    public final StringPath createId = createString("createId");

    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = createDateTime("modifiedDateTime", java.time.LocalDateTime.class);

    public final StringPath modifiedId = createString("modifiedId");

    public QAbstractBaseEntity(String variable) {
        super(AbstractBaseEntity.class, forVariable(variable));
    }

    public QAbstractBaseEntity(Path<? extends AbstractBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractBaseEntity(PathMetadata metadata) {
        super(AbstractBaseEntity.class, metadata);
    }

}

