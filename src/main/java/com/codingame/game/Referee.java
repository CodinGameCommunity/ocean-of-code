package com.codingame.game;

import java.util.Random;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.inject.Inject;

public class Referee extends AbstractReferee {

	public enum Direction {
		N, S, E, W;
	}

	@Inject
	private MultiplayerGameManager<Player> gameManager;
	@Inject
	private GraphicEntityModule entityManager;
	@Inject 
	private GridManager gridManager;
	@Inject
	private EndScreenModule endScreenModule;
	@Inject
	private TooltipModule tooltipModule;
	
	private Random random;
	
	public Random getRandom() {
		return random;
	}

	private int maxTurns = 599;
	private int remainingTurns = 0;
	private int realTurn = -1;


	@Override
	public void init() {
		gameManager.setTurnMaxTime(50);
		gameManager.setMaxTurns(599);
		
		System.err.println(Long.parseLong(gameManager.getGameParameters().get("seed").toString()));
		long seed = gameManager.getSeed();

		random = new Random(seed);
		entityManager.createSprite().setImage("background.jpg").setAnchor(0);

		gridManager.init(random, tooltipModule, gameManager);

		int diff = 87;
		addFrame(1920/2-1080/2+diff, diff, 1080-diff*2, 1080-diff*2, 99);
		gameManager.setFrameDuration(10);
		for (Player p : gameManager.getPlayers()) {
			p.init(this);
		}
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

	public int getLeague() {
		return gameManager.getLeagueLevel();
	}

	public void disablePlayer(Player player, String message) {
		player.deactivate(message);
		player.setScore(-1);
		gameManager.endGame();
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
		for (Player p : gameManager.getPlayers()) {
			try {
				p.initPosition(tooltipModule);
			} catch (TimeoutException e) {
				disablePlayer(p, "Timeout!");
			} catch (GameException e) {
				disablePlayer(p, e.getMessage());
			}
		}
	}

	private void classicTurn(int turn) throws GameException {
		if(remainingTurns > 0){
			Player p = gameManager.getPlayer(realTurn%2);

			p.doGraphics(p.lastCommands.size() - remainingTurns--);
			return;
		}

		turn = ++realTurn;
		Player player = gameManager.getPlayer(turn % 2);
		Player opponent = gameManager.getPlayer((turn + 1) % 2);

		player.sendInputLine(String.format("%d %d %d %d %s", player.getPosition().x, player.getPosition().y,
				player.getLife(), opponent.getLife(), player.getCooldowns()));
		player.sendInputLine(player.getLastResult());
		player.sendInputLine(String.format("%s", opponent.getLastCommand()));
		player.execute();

		// Read inputs
		String output;
		try {
			output = player.getOutputs().get(0);

			player.executeCommand(output);
			remainingTurns = player.lastCommands.size()-1;
			maxTurns += remainingTurns;
			gameManager.setMaxTurns(maxTurns);

		} catch (TimeoutException e) {
			disablePlayer(player, "Timeout!");
		} catch (GameException e) {
			disablePlayer(player, e.getMessage());
		}
	}

	@Override
	public void gameTurn(int turn) {
		try {
			switch (turn) {
			case 1:
				firstTurn();
				break;
			default:
				gameManager.setFrameDuration(500);
				classicTurn(turn);
			}
		}
		catch (Exception e) {
			Player p = gameManager.getPlayer(turn % 2);
			disablePlayer(p, "Unknown exception: " + e.getMessage());
		}
	}
}
