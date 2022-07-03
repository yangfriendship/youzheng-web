package me.youzheng.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrollPage {

    private Integer fromPk;

    private Integer boardNo;

    private int size = 10;

}