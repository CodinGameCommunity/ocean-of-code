package com.codingame.game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	public static class Node {
		int x;
		int y;
		Node parent;

		public Node(int x, int y, Node parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}

		public Node getParent() {
			return this.parent;
		}
		
		public Point getNextPoint() {
			Point p = new Point(x, y);
			
			Node parent = this;
			while (parent.getParent() != null) {
				p = new Point(parent.x, parent.y);
				parent = parent.getParent();
			}
			
			return p;
		}

		public String toString() {
			return "x = " + x + " y = " + y;
		}

		public int getPathLength() {
			Node p = this;

			int result = 0;
			while (p.getParent() != null) {
				p = p.getParent();
				result++;
			}
			
			return result;
		}
	}

	public static Node getPathBFS(boolean[][] maze, Point src, Point dst) {
		
		Queue<Node> q = new LinkedList<Node>();
		maze = Utils.copyGrid(maze);

		q.add(new Node(src.x, src.y, null));

		while (!q.isEmpty()) {
			Node p = q.remove();

			if (p.x == dst.x && p.y == dst.y) {
				return p;
			}

			if (isFree(maze, p.x + 1, p.y)) {
				maze[p.x][p.y] = true;
				Node nextP = new Node(p.x + 1, p.y, p);
				q.add(nextP);
			}

			if (isFree(maze, p.x - 1, p.y)) {
				maze[p.x][p.y] = true;
				Node nextP = new Node(p.x - 1, p.y, p);
				q.add(nextP);
			}

			if (isFree(maze, p.x, p.y + 1)) {
				maze[p.x][p.y] = true;
				Node nextP = new Node(p.x, p.y + 1, p);
				q.add(nextP);
			}

			if (isFree(maze, p.x, p.y - 1)) {
				maze[p.x][p.y] = true;
				Node nextP = new Node(p.x, p.y - 1, p);
				q.add(nextP);
			}
		}
		
		return null;
	}

	public static boolean isFree(boolean[][] maze, int x, int y) {
		if ((x >= 0 && x < maze.length) && (y >= 0 && y < maze[x].length) && !maze[x][y]) {
			return true;
		}
		return false;
	}
}