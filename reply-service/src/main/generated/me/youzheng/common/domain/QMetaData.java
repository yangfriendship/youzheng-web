package me.youzheng.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMetaData is a Querydsl query type for MetaData
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMetaData extends BeanPath<MetaData> {

    private static final long serialVersionUID = -1294385315L;

    public static final QMetaData metaData = new QMetaData("metaData");

    public final DateTimePath<java.time.LocalDateTime> createDateTime = createDateTime("createDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> createdNo = createNumber("createdNo", Integer.class);

    public final StringPath createId = createString("createId");

    public final BooleanPath deleteYn = createBoolean("deleteYn");

    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = createDateTime("modifiedDateTime", java.time.LocalDateTime.class);

    public final StringPath modifiedId = createString("modifiedId");

    public final NumberPath<Integer> modifiedNo = createNumber("modifiedNo", Integer.class);

    public QMetaData(String variable) {
        super(MetaData.class, forVariable(variable));
    }

    public QMetaData(Path<? extends MetaData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMetaData(PathMetadata metadata) {
        super(MetaData.class, metadata);
    }

}

