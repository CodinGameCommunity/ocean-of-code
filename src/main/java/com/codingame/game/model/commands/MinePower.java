package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.Direction;
import com.codingame.game.model.Mine;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

import java.awt.*;

public class MinePower implements IPower {
	private int charges;
	public static String NAME = "MINE";
	private PlayerModel playerModel;

	public MinePower(PlayerModel playerModel){
		this.playerModel = playerModel;
	}

	@Override
	public boolean canExecute(PlayerAction action) throws GameException {
		if(getMaxValue() > charges){
			playerModel.game.addError(playerModel, "Not enough charges of " + NAME);
			return false;
		}

		String[] commands = action.command.split(" ");
		if(commands.length > 2){
			throw new GameException("Invalid amount of params for MINE, should be MINE direction");
		}

		Point target = getTarget(action.command);
		if(!playerModel.gridModel.isEmpty(target)){
			playerModel.game.addError(playerModel, "Mine target outside map or on obstacle");
			return false;
		}

		for(Mine mine : playerModel.mines){
			if(!mine.isBlown && mine.point.equals(target)){
				playerModel.game.addError(playerModel, "Two mines on the same location is not allowed.");
				return false;
			}
		}

		return true;
	}

	public String getName() {
		return NAME;
	}
	public int getCharges() { return charges; }
	public int getMaxValue() {
		return 3;
	}
	public int getPowerIndex() {
		return 3;
	}
	public void increaseCharges() {
		charges = Math.min(getMaxValue(), charges+1);
	}

	private Point getTarget(String command) throws GameException{
		try{
			String[] commands = command.split(" ");
			Direction dir = Direction.getDir(commands[1]);
			Point target = new Point(playerModel.position.x+dir.Dx, playerModel.position.y+dir.Dy);
			return target;
		}
		catch (GameException e){
			throw new GameException(e.getMessage());
		}
		catch (Exception e){
			throw new GameException("Invalid mine command: " + command);
		}
	}

	public void execute(PlayerAction action) throws GameException {
		Point target = getTarget(action.command);
		playerModel.mines.add(new Mine(target));
		playerModel.summaries.add("MINE");
		//playerModel.game.addTooltip(playerModel, "MINE");
		charges = 0;
	}

	public String getActionWindowMessage(PlayerAction action) throws GameException{
		return action.command;
	}
}
