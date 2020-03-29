package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.Direction;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

public class SilencePower implements IPower {
	private int charges;
	public static String NAME = "SILENCE";
	private PlayerModel playerModel;

	public SilencePower(PlayerModel playerModel){
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
			throw new GameException("Invalid amount of params for Silence, should be: SILENCE direction steps");
		}

		getDirection(action.command);
		getDistance(action.command);
		return true;
	}

	@Override
	public void execute(PlayerAction action) throws GameException {
		Direction dir = getDirection(action.command);
		int dist = getDistance(action.command);
		for(int i = 0; i < dist;i++){
			playerModel.move(dir);
		}
		playerModel.summaries.add(NAME);
		//playerModel.game.addTooltip(playerModel, NAME);
		charges = 0;
	}

	@Override
	public String getActionWindowMessage(PlayerAction action) throws GameException {
		return action.command;
	}

	@Override
	public int getMaxValue() {
		return 6;
	}

	@Override
	public int getCharges() {
		return charges;
	}

	@Override
	public int getPowerIndex() {
		return 2;
	}

	@Override
	public void increaseCharges() { charges = Math.min(getMaxValue(), charges+1); }

	private int getDistance(String command) throws GameException{
		try{
			String[] commands = command.split(" ");
			Integer distance = Integer.parseInt(commands[2]);
			if(distance < 0 || distance > 4) {
				throw new GameException("Invalid SILENCE distance");
			}

			return distance;
		}catch (Exception e){
			throw new GameException("Invalid distance on SILENCE: " + command);
		}
	}

	private Direction getDirection(String command) throws GameException{
		try{
			String[] commands = command.split(" ");
			Direction dir = Direction.getDir(commands[1]);
			return dir;
		}
		catch (GameException e){
			throw new GameException(e.getMessage());
		}
		catch (Exception e){
			throw new GameException("Invalid move command: " + command);
		}
	}
}
