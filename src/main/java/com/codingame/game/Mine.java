package com.codingame.game;

import java.awt.*;

public class Mine {
    public final Point point;
    public final Player player;
    public boolean isActive, isBlown;

    public Mine(Point point, Player player) {
        this.point = point;
        this.player = player;
    }
}
