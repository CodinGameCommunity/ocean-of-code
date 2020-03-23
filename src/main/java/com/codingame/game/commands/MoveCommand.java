package com.codingame.game.commands;

import java.awt.Point;

import com.codingame.game.GameException;
import com.codingame.game.GridManager;
import com.codingame.game.Player;
import com.codingame.game.Referee.Direction;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

public class MoveCommand extends Command {
	
	public static String NAME = "MOVE";
	
	private Entity<?>[][] grid = new Entity<?>[GridManager.GRID_SIZE][GridManager.GRID_SIZE];
	
	@Override
	public void init(Player player, Player opponent, MultiplayerGameManager<Player> gameManager, GraphicEntityModule entityManager,
			GridManager gridManager) {
		super.init(player, opponent, gameManager, entityManager, gridManager);
		
		try {
			fillPath(player.getPosition());
		} catch (GameException e) {
			throw new RuntimeException("Should not occur");
		}
	}

	@Override
	public boolean executeIfYouCan(String command) throws GameException {
		
		if (!command.startsWith(NAME+" "))
			return false;
		
		String[] commands = command.split(" ");

		Direction direction = getDirection(commands[1]);
		
		Point result = new Point(player.getPosition().x, player.getPosition().y);

		switch (direction) {
		case E:
			if (gridManager.isEmpty(new Point(result.x+1, result.y)))
				result.x += 1;
			else
				throw new GameException("Invalid MOVE: direction");
			break;
		case N:
			if (gridManager.isEmpty(new Point(result.x, result.y-1)))
				result.y -= 1;
			else
				throw new GameException("Invalid MOVE: direction");
			break;
		case S:
			if (gridManager.isEmpty(new Point(result.x, result.y+1)))
				result.y += 1;
			else
				throw new GameException("Invalid MOVE: direction");
			break;
		case W:
			if (gridManager.isEmpty(new Point(result.x-1, result.y)))
				result.x -= 1;
			else
				throw new GameException("Invalid MOVE: direction");
			break;
		}

		if (grid[result.x][result.y] != null) {
			throw new GameException("Invalid MOVE: obstacle or already visited cell");
		}
		
		fillPath(result);
		player.setPosition(result);

		if (commands.length >= 3) {
			
			Command c = player.getCommand(commands[2]);
			
			if (c == null || !(c instanceof Power)) {
				gameManager.addTooltip(player, String.format("Invalid power to charge '%s'", commands[2]));
			}
			else
			{
				((Power)c).chargePower();
				toActionWindow = NAME + " " + direction + " " + commands[2];
			}
		}
		else
		{
			toActionWindow = NAME + " " + direction + " ";
		}
		
		setSummary(String.format("MOVE %s", direction.toString()));
		position = new Point(player.getPosition());
		return true;
	}

	private Point position;

	@Override
	public void doGraphics() throws GameException {
		gridManager.updatePlayerPosition(player, false, position);
	}

	public void fillPath(Point p) throws GameException {
		grid[p.x][p.y] = gridManager.fillCell(p, player.getColorToken(), player);
	}
	
	public boolean hasVisited(Point p) {
		return grid[p.x][p.y] != null;
	}
	
	public void surface() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				Entity<?> path = grid[i][j];

				if (path != null) {
					path.setAlpha(0);
				}

				grid[i][j] = null;
			}
		}

		Point currentPoint = player.getPosition();
		grid[currentPoint.x][currentPoint.y] = gridManager.fillCell(currentPoint, player.getColorToken(), player);
	}
}
