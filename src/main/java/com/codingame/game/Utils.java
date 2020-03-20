package com.codingame.game;

public class Utils {
	public static boolean[][] copyGrid(boolean[][] source) {
		boolean[][] result = new boolean[source.length][source.length];

		for (int i = 0; i < source.length; i++)
			for (int j = 0; j < source[i].length; j++)
				result[i][j] = source[i][j];

		return result;
	}
}
