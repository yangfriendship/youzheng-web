package me.youzheng.boardservice.board.mapper;

import me.youzheng.boardservice.board.domain.Board;
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
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    void map(Board source, @MappingTarget Board target);

}
