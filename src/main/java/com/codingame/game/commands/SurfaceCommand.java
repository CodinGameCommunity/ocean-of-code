package com.codingame.game.commands;

import com.codingame.game.GameException;
import com.codingame.game.GridManager;
import com.codingame.game.Player;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.Circle;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class SurfaceCommand extends Command {

	public static String NAME = "SURFACE";

	@Override
	public void init(Player player, Player opponent, MultiplayerGameManager<Player> gameManager, GraphicEntityModule entityManager, GridManager gridManager) {
		super.init(player, opponent, gameManager, entityManager, gridManager);
	}

	@Override
	public boolean executeIfYouCan(String command) throws GameException {
		if (!command.trim().equals(NAME)) return false;
		
		((MoveCommand)player.getCommand(MoveCommand.NAME)).surface();
		
		player.reduceLife();
		
		setSummary(String.format("SURFACE %d", player.getSector()));
		toActionWindow = getSummary();
		//gameManager.addTooltip(player, "Surface");
		return true;
	}


	@Override
	public void doGraphics() throws GameException {
		gridManager.surface(player);
	}
}
