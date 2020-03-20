package com.codingame.game.commands;

import com.codingame.game.GameException;
import com.codingame.gameengine.module.entities.Circle;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class SurfaceCommand extends Command {

	public static String NAME = "SURFACE";
	
	@Override
	public boolean executeIfYouCan(String command) throws GameException {
		if (!command.startsWith(NAME)) return false;
		
		((MoveCommand)player.getCommand(MoveCommand.NAME)).surface();
		
		player.reduceLife();
		
		animate();

		setSummary(String.format("SURFACE %d", player.getSector()));
		gameManager.addTooltip(player, "Surface");
		return true;
	}
	
	private void animate() {
		Circle circle = gridManager.createCircle(player.getPosition(), player.getColorToken());
		circle.setFillAlpha(0);
		
		entityManager.commitEntityState(0, circle);
		circle.setFillAlpha(0.5);
		entityManager.commitEntityState(0.2, circle);
		
		circle.setScale(10);
		circle.setAlpha(0);
		entityManager.commitEntityState(1, circle);
		
		Group submarine = gridManager.getPlayers().get(player);
		submarine.setScale(2);
		entityManager.commitEntityState(0.5, submarine);
		submarine.setScale(1);
		entityManager.commitEntityState(1, submarine);
	}

}
