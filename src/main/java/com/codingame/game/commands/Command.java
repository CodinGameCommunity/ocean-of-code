package com.codingame.game.commands;

import com.codingame.game.GameException;
import com.codingame.game.GridManager;
import com.codingame.game.Player;
import com.codingame.game.Referee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

import java.awt.*;

public abstract class Command {

	protected Player player;
	protected Player opponent;
	protected MultiplayerGameManager<Player> gameManager;
	protected GraphicEntityModule entityManager;
	protected GridManager gridManager;
	private String result = null;
	private String summary = null;
	protected boolean wasValid = true;
	public String toActionWindow;

	public void init(Player player, Player opponent, MultiplayerGameManager<Player> gameManager, GraphicEntityModule entityManager,
			GridManager gridManager) {
		this.player = player;
		this.opponent = opponent;
		this.gameManager = gameManager;
		this.entityManager = entityManager;
		this.gridManager = gridManager;
	}

	public abstract boolean executeIfYouCan(String command) throws GameException;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSummary() {
		return summary;
	}


	public void doGraphics() throws GameException{

	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	protected int getDx(Referee.Direction direction){
		if(direction == Referee.Direction.N || direction == Referee.Direction.S) return 0;
		if(direction == Referee.Direction.E) return 1;
		return -1;
	}

	protected int getDy(Referee.Direction direction){
		if(direction == Referee.Direction.E || direction == Referee.Direction.W) return 0;
		if(direction == Referee.Direction.S) return 1;
		return -1;
	}

	protected boolean isValidLocation(Point point){
		return point.x >= 0 && point.y >= 0 && point.x < 15 && point.y < 15;
	}

	public boolean isValidCommand(){
		return wasValid;
	}

	public Referee.Direction getDirection(String dir) throws GameException{
		try{
			return Referee.Direction.valueOf(dir);
		}catch (Exception e){
			throw new GameException("Invalid direction: " + dir + ". Legal values are: N, S, E or W.");
		}
	}
}
