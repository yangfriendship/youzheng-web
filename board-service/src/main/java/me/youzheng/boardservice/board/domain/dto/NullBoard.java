package me.youzheng.boardservice.board.domain.dto;

import me.youzheng.boardservice.board.domain.Board;

public final class NullBoard extends Board {

    private NullBoard() {
    }

    private static final class InstanceHolder {

        static final NullBoard INSTANCE = new NullBoard();
    }

    public static NullBoard getInstance() {
        return InstanceHolder.INSTANCE;
    }

}