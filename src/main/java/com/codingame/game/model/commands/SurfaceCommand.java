package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

public class SurfaceCommand implements ICommand {

	public static String NAME = "SURFACE";
	private PlayerModel playerModel;

	public SurfaceCommand(PlayerModel playerModel){
		this.playerModel = playerModel;
	}

	public String getName() { return NAME; }

	public boolean canExecute(PlayerAction action) throws GameException {
		return true;
	}

	public void execute(PlayerAction action) throws GameException {
		playerModel.game.addTooltip(playerModel, NAME);
		playerModel.surface();
		playerModel.summaries.add(String.format("SURFACE %d", playerModel.getSector()));
	}

	public String getActionWindowMessage(PlayerAction action) throws GameException {
		return action.activator;
	}
}
