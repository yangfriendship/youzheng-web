package me.youzheng.boardservice.board.domain.dto;

public final class NullBoardDto extends BoardDto {

    private NullBoardDto() {
    }

    private static final class InstanceHolder {

        static final NullBoardDto INSTANCE = new NullBoardDto();
    }

    public static NullBoardDto getInstance() {
        return InstanceHolder.INSTANCE;
    }

}