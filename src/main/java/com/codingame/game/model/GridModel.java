package com.codingame.game.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GridModel {
    public static final int GRID_SIZE = 15;
    public boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
    public Map<Point, String> tiles;

    public boolean isEmpty(Point p) {
        return p.x>=0 && p.x<GRID_SIZE && p.y>=0 && p.y<GRID_SIZE && !grid[p.x][p.y];
    }

    public void initialize(Random rand){
        boolean[][] result = new boolean[GRID_SIZE][GRID_SIZE];

        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                result[x][y] = false;
            }
        }

        // create random islands
        for (int i = 0; i < rand.nextInt(26) + 5; i++) {

            int x = rand.nextInt(15);
            int y = rand.nextInt(15);

            result[x][y] = true;
        }

        grid = Utils.copyGrid(result);

        // increase the island tiles
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                if (result[x][y]) {
                    if (y - 1 >= 0) {
                        grid[x][y - 1] = true;
                    }

                    if (x + 1 < GRID_SIZE) {
                        grid[x + 1][y] = true;

                        if (y - 1 >= 0) {
                            grid[x + 1][y - 1] = true;
                        }
                    }
                }
            }
        }

        do {
            tiles = checkError(grid);
        } while (tiles == null);
    }

    private Map<Point, String> checkError(boolean[][] grid) {
        String[] tiles = new String[] { "11.0.111", ".11111.0", ".0.0.111", "11.0.0.1", ".111.0.0", ".0.111.0",
                "1111.0.1", ".0.11111", "11111111", "11111101", "11110111", "11011111", "01111111" };

        Map<Point, String> tileSprites = new HashMap<Point, String>();

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                boolean island = grid[x][y];

                if (!island)
                    continue;

                // create map
                StringBuilder map = new StringBuilder();
                map.append(y - 1 < 0 || x - 1 < 0 || grid[x - 1][y - 1] ? "1" : "0");
                map.append(y - 1 < 0 || grid[x][y - 1] ? "1" : "0");
                map.append(y - 1 < 0 || x + 1 >= GRID_SIZE || grid[x + 1][y - 1] ? "1" : "0");
                map.append(x + 1 >= GRID_SIZE || grid[x + 1][y] ? "1" : "0");
                map.append(y + 1 >= GRID_SIZE || x + 1 >= GRID_SIZE || grid[x + 1][y + 1] ? "1" : "0");
                map.append(y + 1 >= GRID_SIZE || grid[x][y + 1] ? "1" : "0");
                map.append(y + 1 >= GRID_SIZE || x - 1 < 0 || grid[x - 1][y + 1] ? "1" : "0");
                map.append(x - 1 < 0 || grid[x - 1][y] ? "1" : "0");

                String sprite = null;
                for (int i = 0; i < tiles.length; i++) {
                    if (map.toString().matches(tiles[i])) {
                        sprite = String.format("island/%s.png", tiles[i].replaceAll("\\.", "x"));
                        tileSprites.put(new Point(x, y), sprite);
                        break;
                    }
                }

                if (sprite == null) {
                    // fix error
                    grid[Math.max(x - 1, 0)][Math.max(0, y - 1)] = true;
                    grid[x][Math.max(0, y - 1)] = true;
                    grid[Math.min(x + 1, GRID_SIZE - 1)][Math.max(0, y - 1)] = true;
                    grid[Math.max(x - 1, 0)][Math.max(0, y)] = true;
                    grid[Math.min(x + 1, GRID_SIZE - 1)][Math.max(0, y)] = true;
                    grid[Math.max(x - 1, 0)][Math.min(y + 1, GRID_SIZE - 1)] = true;
                    grid[x][Math.min(y + 1, GRID_SIZE - 1)] = true;
                    grid[Math.min(x + 1, GRID_SIZE - 1)][Math.min(y + 1, GRID_SIZE - 1)] = true;

                    return null;
                }
            }
        }

        return tileSprites;
    }
}
