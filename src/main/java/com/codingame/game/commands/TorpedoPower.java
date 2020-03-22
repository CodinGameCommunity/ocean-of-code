package com.codingame.game.commands;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import com.codingame.game.BFS;
import com.codingame.game.BFS.Node;
import com.codingame.game.GameException;
import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.Line;

public class TorpedoPower extends Power {
	
	public static String NAME = "TORPEDO";

	@Override
	public boolean executeIfYouCan(String command) throws GameException {		
		String[] commands = command.split(" ");
		if (!commands[0].startsWith(NAME)) return false;
		wasValid = true;
		consume();
		if(!wasValid) return true;
		
		Point target = new Point(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
		
		// calculate path
		Node path = BFS.getPathBFS(gridManager.getGrid(), player.getPosition(), target);
		
		if (path == null || path.getPathLength() > 4) {
			wasValid = false;
			gameManager.addToGameSummary("Tried to fire out of range");
			return true;
		}
		
		//gameManager.addTooltip(player, "Torpedo");
		
		List<Player> players = gameManager.getPlayers();
		
		for(Iterator<Player> it = players.iterator(); it.hasNext();) {
			Player opponent = it.next();
			
			if (Math.abs(opponent.getPosition().x - target.x) <= 1 && Math.abs(opponent.getPosition().y - target.y) <= 1) {
				opponent.reduceLife();
			}
			
			// additional point if the target is the exact position of the opponent
			if (opponent.getPosition().equals(target)) {
				opponent.reduceLife();
			}
		}
		
		setSummary(String.format("%s %d %d", NAME, target.x, target.y));
		toActionWindow = getSummary();
		origin = new Point(player.getPosition().x, player.getPosition().y);
		return true;
	}

	private Point origin;

	@Override
	public void doGraphics() throws GameException {
		String[] commands = toActionWindow.split(" ");
		Point target = new Point(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
		gridManager.torpedo(target, player, origin);
	}

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
		return 0;
	}	
}
