package com.codingame.game.commands;

import java.awt.Point;

import com.codingame.game.GameException;
import com.codingame.game.GridManager;
import com.codingame.game.Player;
import com.codingame.game.Referee.Direction;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

public class SilencePower extends Power {

	public static String NAME = "SILENCE";
	
	private MoveCommand moveCommand;
	
	@Override
	public void init(Player player, Player opponent, MultiplayerGameManager<Player> gameManager, GraphicEntityModule entityManager,
			GridManager gridManager) {
		super.init(player, opponent, gameManager, entityManager, gridManager);
		
		moveCommand = (MoveCommand) player.getCommand(MoveCommand.NAME);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaxValue() {
		return 6;
	}

	@Override
	public int getPowerIndex() {
		return 2;
	}

	@Override
	public boolean executeIfYouCan(String command) throws GameException {
		if (!command.startsWith(NAME))
			return false;
		
		consume();
		if(!wasValid) return true;
		
		String[] commands = command.split(" ");
		if(commands.length != 3){
			throw new GameException("Invalid amount of params for Silence, should be: SILENCE direction steps");
		}

		Direction direction = Direction.valueOf(commands[1]);
		
		Integer distance = Integer.parseInt(commands[2]);
		if(distance < 0 || distance > 4) {
			throw new GameException("Invalid silence distance");
		}
		
		setSummary("SILENCE");
		gameManager.addTooltip(player, "Silence");
		player.isSilenced = true;
		for (int i = 1; i <= distance; i++) {
			Point result = new Point(player.getPosition().x, player.getPosition().y);
			
			switch (direction) {
			case E:
				if (gridManager.isEmpty(new Point(result.x+1, result.y)))
					result.x += 1;
				else
					throw new GameException("Invalid SILENCE: direction");
				break;
			case N:
				if (gridManager.isEmpty(new Point(result.x, result.y-1)))
					result.y -= 1;
				else
					throw new GameException("Invalid SILENCE: direction");
				break;
			case S:
				if (gridManager.isEmpty(new Point(result.x, result.y+1)))
					result.y += 1;
				else
					throw new GameException("Invalid SILENCE: direction");
				break;
			case W:
				if (gridManager.isEmpty(new Point(result.x-1, result.y)))
					result.x -= 1;
				else
					throw new GameException("Invalid SILENCE: direction");
				break;
			}

			if (moveCommand.hasVisited(result)) {
				throw new GameException("Invalid MOVE: obstacle or already visited cell");
			}
			
			moveCommand.fillPath(result);
			if(i == distance){
				player.isSilenced = false;
			}
			player.setPosition(result);

			if(player.getLife() <= 0) return true; // death by mines
		}
		return true;
	}
}
