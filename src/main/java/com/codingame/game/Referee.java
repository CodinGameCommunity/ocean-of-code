package com.codingame.game;

import java.util.ArrayList;
import java.util.Random;

import com.codingame.game.model.Game;
import com.codingame.game.model.IPlayerManager;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.inject.Inject;

public class Referee extends AbstractReferee implements IPlayerManager {
	@Inject
	private MultiplayerGameManager<Player> gameManager;
	@Inject
	private GraphicEntityModule entityManager;
	@Inject
	private EndScreenModule endScreenModule;
	@Inject
	private TooltipModule tooltipModule;
	private ViewController viewController;
	private Random random;
	private Game game;


	@Override
	public void init() {
		gameManager.setTurnMaxTime(50);
		gameManager.setMaxTurns(1000);
		
		System.err.println(Long.parseLong(gameManager.getGameParameters().get("seed").toString()));
		long seed = gameManager.getSeed();

		random = new Random(seed);
		entityManager.createSprite().setImage("background.jpg").setAnchor(0);

		int diff = 87;
		addFrame(1920/2-1080/2+diff, diff, 1080-diff*2, 1080-diff*2, 99);
		game = new Game(this, random);
		viewController = new ViewController(this, game, entityManager, new ArrayList<>(gameManager.getPlayers()), tooltipModule);
		viewController.preInitialize();
	}

	public int getLeague() {
		return gameManager.getLeagueLevel();
	}

	private String getLifeString(int amount){
		if(Math.abs(amount)==1) return " life";
		return " lives";
	}
	@Override
	public void onEnd() {
		int[] scores = gameManager.getPlayers().stream().mapToInt(p -> p.getScore()).toArray();
		String[] texts = new String[]{ gameManager.getPlayer(0).getScore()+getLifeString(gameManager.getPlayer(0).getScore()), gameManager.getPlayer(1).getScore()+getLifeString(gameManager.getPlayer(1).getScore())};
		endScreenModule.setScores(scores, texts);
		endScreenModule.setTitleRankingsSprite("logo.png");
	}

	private void firstTurn() {
		game.initialize(getLeague());
		viewController.initialize();
	}

	private void classicTurn()  {
		game.onRound();
		viewController.onRound();
	}

	@Override
	public void gameTurn(int turn) {
		try{
			if(turn==1){
				gameManager.setFrameDuration(10);
				firstTurn();
			}else{
				gameManager.setFrameDuration(500);
				classicTurn();
			}
		}
		catch (Exception e){
			if(game.activePlayer != null){
				disablePlayer(game.activePlayer.index, e.getMessage());
				gameManager.addToGameSummary(e.getMessage());
				gameManager.addTooltip(gameManager.getPlayers().get(game.activePlayer.index), e.getMessage());
			}
		}
	}

	@Override
	public String getOutputs(int playerId) throws TimeoutException {
		return gameManager.getPlayer(playerId).getOutputs().get(0);
	}

	@Override
	public void sendData(String[] lines, int playerId) {
		for(String line : lines){
			gameManager.getPlayer(playerId).sendInputLine(line);
		}
	}

	@Override
	public void execute(int playerId) {
		gameManager.getPlayer(playerId).execute();
	}

	@Override
	public void updateScore(int playerId, int score) {
		gameManager.getPlayer(playerId).setScore(score);
	}

	@Override
	public void disablePlayer(int playerId, String reason) {
		gameManager.getPlayer(playerId).deactivate(reason);
		gameManager.getPlayer(playerId).setScore(-1);
		gameManager.endGame();
	}

	@Override
	public void endGame() {
		gameManager.endGame();
	}

	@Override
	public void addTooltip(int playerId, String message) {
		gameManager.addTooltip(gameManager.getPlayer(playerId), message);
	}

	@Override
	public void addGameSummary(int playerId, String message) {
		gameManager.addToGameSummary(message);
	}

	private void addFrame(int x, int y, int width, int height, int z){
		entityManager.createRectangle()
				.setLineColor(0xefefef)
				.setLineWidth(10)
				.setZIndex(z)
				.setX(x)
				.setY(y)
				.setWidth(width)
				.setHeight(height)
				.setFillAlpha(0);
	}
}
