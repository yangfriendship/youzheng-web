package me.youzheng.replyservice.reply.mapper;


import me.youzheng.replyservice.reply.domain.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ReplyMapper {

    ReplyMapper INSTANCE = Mappers.getMapper(ReplyMapper.class);

    void map(Reply source, @MappingTarget Reply target);

}
