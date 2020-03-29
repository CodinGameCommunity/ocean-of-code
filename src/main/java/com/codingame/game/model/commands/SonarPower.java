package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

public class SonarPower implements IPower {
	private int charges;
	public static String NAME = "SONAR";
	private PlayerModel playerModel;

	public SonarPower(PlayerModel playerModel){

		this.playerModel = playerModel;
	}

	public String getName() { return NAME; }

	@Override
	public boolean canExecute(PlayerAction action) throws GameException {
		if(getMaxValue() > charges){
			playerModel.game.addError(playerModel, "Not enough charges of " + NAME);
			return false;
		}

		String[] commands = action.command.split(" ");
		if(commands.length != 2){
			throw new GameException("Invalid amount of params for SONAR, should be: SONAR sectorId");
		}

		getSector(action.command);
		return true;
	}

	@Override
	public void execute(PlayerAction action) throws GameException {
		int sector = getSector(action.command);
		playerModel.sonarResult = playerModel.getOpponent().getSector() == sector ? "Y" : "N";
		playerModel.summaries.add(String.format("SONAR %d", sector));
		playerModel.game.addTooltip(playerModel, String.format("SONAR %d", sector));
		charges = 0;
	}

	@Override
	public String getActionWindowMessage(PlayerAction action) throws GameException {
		return action.command;
	}

	@Override
	public int getMaxValue() {
		return 4;
	}

	@Override
	public int getCharges() {
		return charges;
	}

	@Override
	public int getPowerIndex() {
		return 1;
	}

	@Override
	public void increaseCharges() { charges = Math.min(getMaxValue(), charges+1);}

	private int getSector(String command) throws GameException {
		try {
			String[] commands = command.split(" ");
			Integer sector = Integer.parseInt(commands[1]);
			if (sector < 1 || sector > 9){
				throw new GameException("Invalid sector to scan. Must be a number between 1 and 9");
			}

			return sector;
		}
		catch(Exception e){
			throw new GameException("Invalid distance on silence: " + command);
		}
	}
}
