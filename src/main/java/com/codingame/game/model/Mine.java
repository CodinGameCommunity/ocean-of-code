package com.codingame.game.model;

import java.awt.*;

public class Mine {
    public final Point point;
    public boolean isActive, isBlown;

    public Mine(Point point) {
        this.point = point;
    }
}
