package com.codingame.game.commands;

import com.codingame.game.GameException;
import com.codingame.game.Mine;
import com.codingame.game.Referee;

import java.awt.*;

public class MinePower extends Power {

	public static String NAME = "MINE";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaxValue() {
		return 3;
	}

	@Override
	public int getPowerIndex() {
		return 3;
	}

	@Override
	public boolean executeIfYouCan(String command) throws GameException {
		if (!command.startsWith(NAME)) return false;
		wasValid = true;
		consume();
		if(!wasValid) return true;

		String[] commands = command.split(" ");
		setSummary("MINE");
		Referee.Direction direction = Referee.Direction.valueOf(commands[1]);
		Point target = new Point(player.getPosition().x + getDx(direction), player.getPosition().y+getDy((direction)));

		if(!gridManager.isEmpty(target)){
			wasValid = false;
			gameManager.addToGameSummary("Mine target outside map or on obstacle");
			gameManager.addTooltip(player, "Can't place mine here");
			return true;
		}

		if(gridManager.hasMine(target, player)){
			wasValid = false;
			gameManager.addToGameSummary("Double mine not allowed");
			gameManager.addTooltip(player, "Can't place double mines");
			return true;
		}

		Mine mine = new Mine(target, player);
		gridManager.addMine(mine);
		gameManager.addTooltip(player, "Mine");
		return true;
	}

}
