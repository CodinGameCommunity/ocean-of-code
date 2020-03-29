package com.codingame.game.model.commands;


import com.codingame.game.GameException;
import com.codingame.game.model.Direction;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

public class MoveCommand implements ICommand {
	public static String NAME = "MOVE";
	private PlayerModel playerModel;

	public MoveCommand(PlayerModel playerModel) {
		this.playerModel = playerModel;
	}

	public String getName() { return NAME; }

	@Override
	public boolean canExecute(PlayerAction action) throws GameException {
		String[] commands = action.command.split(" ");
		if(commands.length > 3){
			throw new GameException("Invalid amount of parameters to MOVE. Should be MOVE direction power");
		}

		getDirection(action.command);
		getPower(action.command);
		return true;
	}

	@Override
	public void execute(PlayerAction action) throws GameException {
		Direction direction = getDirection(action.command);
		String power = getPower(action.command);
		playerModel.move(direction);
		playerModel.chargePower(power);
		playerModel.summaries.add(String.format("MOVE %s", direction.Name));
	}

	@Override
	public String getActionWindowMessage(PlayerAction action) throws GameException {
		return action.command;
	}

	private String getPower(String command) throws  GameException{
		String[] commands = command.split(" ");
		if(commands.length != 3) return "";
		String power = commands[2].trim();
		if (!playerModel.hasPower(power)){
			throw new GameException("Invalid power to charge: " + power);
		}
		return power;
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
