package com.codingame.game.model.commands;

import java.awt.Point;

import com.codingame.game.model.BFS;
import com.codingame.game.model.BFS.Node;
import com.codingame.game.GameException;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

public class TorpedoPower implements IPower {
	private int charges;
	public static String NAME = "TORPEDO";
	private PlayerModel playerModel;

	public TorpedoPower(PlayerModel playerModel){
		this.playerModel = playerModel;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean canExecute(PlayerAction action) throws GameException {
		if(getMaxValue() > charges){
			playerModel.game.addError(playerModel, "Not enough charges of " + NAME);
			return false;
		}

		String[] commands = action.command.split(" ");
		if(commands.length != 3){
			throw new GameException("Invalid amount of params for TORPEDO, should be TORPEDO x y");
		}

		Point target = getTarget(action.command);
		Node path = BFS.getPathBFS(playerModel.gridModel.grid, playerModel.position, target);

		if (path == null || path.getPathLength() > 4) {
			playerModel.game.addError(playerModel,"Tried to fire out of range");
			return false;
		}

		return true;
	}

	@Override
	public void execute(PlayerAction action) throws GameException {
		playerModel.game.addTooltip(playerModel, action.command);
		Point target = getTarget(action.command);
		playerModel.game.explode(target);
		playerModel.summaries.add(String.format("%s %d %d", NAME, target.x, target.y));
		charges = 0;
	}

	public String getActionWindowMessage(PlayerAction action) throws GameException {
		return action.command;
	}

	public int getMaxValue() {
		return 3;
	}

	public int getCharges() { return charges; }

	public int getPowerIndex() {
		return 0;
	}

	public void increaseCharges() { charges = Math.min(getMaxValue(), charges+1);}

	private Point getTarget(String command) throws GameException{
		try{
			String[] commands = command.split(" ");
			Point target = new Point(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
			return target;
		}
		catch (Exception e){
			throw new GameException("Invalid params to Torpedo. Should be TORPEDO x y");
		}
	}
}
