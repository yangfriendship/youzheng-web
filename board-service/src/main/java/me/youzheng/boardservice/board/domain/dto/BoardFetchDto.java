package me.youzheng.boardservice.board.domain.dto;

import lombok.Data;
import me.youzheng.common.domain.MetaData;

@Data
public class BoardFetchDto {

    private String title;

    private String content;

    private MetaData metaData;

}