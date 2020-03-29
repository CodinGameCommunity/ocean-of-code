package com.codingame.game.model;

import com.codingame.game.GameException;

public class Direction {
    public int Dx, Dy;
    public String Name;
    public Direction(int dx, int dy, String name){
        Dx = dx;
        Dy = dy;
        Name = name;
    }

    private static Direction[] directions = {
            new Direction(-1, 0, "W"),
            new Direction(1, 0, "E"),
            new Direction(0, -1, "N"),
            new Direction(0, 1, "S"),
    };

    public static Direction getDir(String dir) throws GameException {
        for(Direction d : directions){
            if(d.Name.equals(dir)) {
                return d;
            }
        }
        throw new GameException("Invalid direction: " + dir + ". Legal values are: N, S, E or W.");
    }
}
