package com.codingame.game.commands;

import com.codingame.game.GameException;

public class SonarPower extends Power {

	public static String NAME = "SONAR";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getMaxValue() {
		return 4;
	}

	@Override
	public int getPowerIndex() {
		return 1;
	}

	@Override
	public boolean executeIfYouCan(String command) throws GameException {
		if (!command.startsWith(NAME))
			return false;

		wasValid = true;
		consume();
		if(!wasValid) return true;

		String[] commands = command.split(" ");
		try {
			int sector = Integer.parseInt(commands[1]);
			if(sector < 1 || sector > 9){
				wasValid = false;

			}

			setResult(sector == opponent.getSector() ? "Y" : "N");
			setSummary(String.format("SONAR %d", sector));
			gameManager.addTooltip(player, "Sonar " + sector);
			gridManager.addSonar(sector);

		} catch (NumberFormatException e) {
			throw new GameException("Invalid Sonar command: sector not found");
		}

		return true;
	}
}
