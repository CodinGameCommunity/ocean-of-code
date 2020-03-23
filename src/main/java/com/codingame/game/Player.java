package com.codingame.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.codingame.game.commands.*;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.inject.Inject;

public class Player extends AbstractMultiplayerPlayer {

	@Inject
	private GridManager gridManager;
	@Inject
	private GraphicEntityModule entityManager;
	@Inject
	private MultiplayerGameManager<Player> gameManager;
	@Inject
	private Referee referee;

	private Text messageText;

	public boolean isSilenced;
	private Point position;
	private Map<String, Command> commands = new HashMap<String, Command>();
	private int life = 6;
	private List<Circle> graphicLife = new ArrayList<Circle>();
	public List<Command> lastCommands = new ArrayList<Command>();

	public String message;

	@Override
	public int getExpectedOutputLines() {
		return 1;
	}

	public String getLastCommand() {

		List<String> summaries = new ArrayList<String>();

		for (Iterator<Command> it = lastCommands.iterator(); it.hasNext();) {
			Command c = it.next();
			String summary = c.getSummary();
			if (summary != null) {
				summaries.add(summary);
			}
		}

		return summaries.size() > 0 ? String.join("|", summaries) : "NA";
	}

	public String getLastResult() {

		List<String> summaries = new ArrayList<String>();

		for (Iterator<Command> it = lastCommands.iterator(); it.hasNext();) {
			Command c = it.next();
			String summary = c.getResult();
			if (summary != null) {
				summaries.add(summary);
			}
		}

		return summaries.size() > 0 ? String.join("|", summaries) : "NA";
	}

	public void init(Referee referee) {
		createNickname();
		createPlayerFrame();
		createLife();
		sendGrid();
		createCommands(referee);
		createMessageText();
		setScore(life);
	}

	private void createMessageText(){
		messageText = entityManager.createText("")
				.setFontFamily("Arial")
				.setFillColor(0xffffff)
				.setX(getIndex()==0?30:1890)
				.setMaxWidth(400)
				.setY(370)
				.setFontSize(30)
				.setZIndex(100);

		if (getIndex() == 1) {
			messageText.setAnchorX(1);
		}
	}

	private void createPlayerFrame(){
		Sprite sprite = entityManager.createSprite()
				.setImage(getAvatarToken()).setAnchorX(0.5).setBaseHeight(200).setBaseWidth(200).setY(50).setX(1080/4);

		addFrame(1080/4-5-100, 45, 205, 205, 1);

		if (getIndex() == 1) {
			sprite.setX(1920-sprite.getX());
		}
	}

	private void addFrame(int x, int y, int width, int height, int z){
		Rectangle rect = entityManager.createRectangle()
				.setLineColor(getColorToken())
				.setLineWidth(10)
				.setZIndex(z)
				.setX(x)
				.setY(y)
				.setWidth(width)
				.setHeight(height)
				.setFillAlpha(0);

		if (getIndex() == 1) {
			rect.setX(1920-rect.getX()-rect.getWidth());
		}
	}

	private void createNickname() {
		Text nickname = entityManager.createText(getNicknameToken())
				.setFontFamily("Arial")
				.setX(1080/4)
				.setY(270)
				.setZIndex(20)
				.setAnchorX(0.5)
				.setMaxWidth(400)
				.setFontSize(60)
				.setFillColor(getColorToken());

		if (getIndex() == 1) {
			nickname.setX(1920-nickname.getX());
		}
	}

	private void createLife() {
		// create life
		for (int i = 0; i < life; i++) {

			int x = 1080/4+1080/6-10;
			int y = 35 + (life-i-1) * 40;

			if (getIndex() == 1) {
				x = 1920 - x;
			}

			//entityManager.createCircle().setX(x).setY(y)
			//		.setFillColor(0x555555).setRadius(15).setAlpha(1).setFillAlpha(1).setLineColor(0x000000).setLineWidth(3);

			graphicLife.add(entityManager.createCircle().setX(x).setY(y).setFillColor(getColorToken()).setRadius(12)
					.setAlpha(0.8).setLineColor(0x000000)
					.setLineWidth(4));
		}
	}

	private void sendGrid() {

		boolean[][] grid = gridManager.getGrid();

		// send grid
		sendInputLine(String.format("%d %d %d", GridManager.GRID_SIZE, GridManager.GRID_SIZE, getIndex()));

		// send the grid
		for (int y = 0; y < GridManager.GRID_SIZE; y++) {
			StringBuffer line = new StringBuffer();
			for (int x = 0; x < GridManager.GRID_SIZE; x++) {
				line.append(grid[x][y] ? "x" : ".");
			}
			sendInputLine(line.toString());
		}
	}

	public void initPosition(TooltipModule tooltipModule) throws GameException, TimeoutException {
		execute();

		// Read inputs
		String output = getOutputs().get(0);

		if (!output.matches("^[0-9]{1,2} [0-9]{1,2}$"))
			throw new GameException("Cannot read initial coordinates. Expected format: 'X Y'");
		String[] coordinates = output.split(" ");
		position = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

		if (!gridManager.isEmpty(position))
			throw new GameException("Invalid coordinates!");
		gridManager.createPlayer(this, tooltipModule);

		for (Iterator<Command> it = commands.values().iterator(); it.hasNext();) {
			it.next().init(gameManager.getPlayer(getIndex()), gameManager.getPlayer(getIndex() == 0 ? 1 : 0),
					gameManager, entityManager, gridManager);
		}
	}

	private void createCommands(Referee referee) {

		commands.put(MoveCommand.NAME, new MoveCommand());
		commands.put(SurfaceCommand.NAME, new SurfaceCommand());
		commands.put(TorpedoPower.NAME, new TorpedoPower());
		commands.put(MessageCommand.NAME, new MessageCommand());

		if (referee.getLeague() >= 2) {
			commands.put(SilencePower.NAME, new SilencePower());
			commands.put(SonarPower.NAME, new SonarPower());
		}

		if (referee.getLeague() >= 3) {
			commands.put(MinePower.NAME, new MinePower());
			commands.put(TriggerCommand.NAME, new TriggerCommand());
		}
	}

	public int getLife() {
		return life;
	}

	public void reduceLife() {

		if (life > 0) {
			life--;
			graphicLife.get(life).setFillColor(0x555555).setScale(0.5);
			gameManager.addTooltip(this, getNicknameToken() + " lost a life");
		}

		if (life <= 0) {
			System.err.println("DISABLE PLAYER no life");
			referee.disablePlayer(this, "No more life");
		}
		this.setScore(life);
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point p) {
		this.position = p;

	}

	public int getSector() {
		return (int) (Math.ceil((position.x + 1) / 5.0) + Math.floor(position.y / 5.0) * 3);
	}

	public Command getCommand(String command) {
		return commands.get(command);
	}

	public String getCooldowns(){
		return getCooldown(TorpedoPower.NAME)
				+ " " + getCooldown(SonarPower.NAME)
				+ " " + getCooldown(SilencePower.NAME)
				+ " " + getCooldown(MinePower.NAME);
	}

	private int getCooldown(String commandName){
		if(commands.containsKey(commandName)){
			Power power = (Power)getCommand(commandName);
			return power.getMaxValue()-power.charge;
		}

		return -1;
	}


	public void executeCommand(String command) throws GameException {
		gridManager.beforeRound();
		message = "";
		String[] commandStr = command.split("\\|");
		ArrayList<String> cmds = new ArrayList<>();
		lastCommands = new ArrayList<Command>();
		for (int i = 0; i < commandStr.length; i++) {

			String cStr = commandStr[i].trim();
			boolean done= false;
			for (Iterator<Command> it = commands.values().iterator(); it.hasNext();) {
				Command c = it.next();

				if (c.executeIfYouCan(cStr)) {
					if (lastCommands.contains(c)) {
						throw new GameException("Two equal commands executed.");
					}

					if(c.isValidCommand()){
						lastCommands.add(c);
						cmds.add(c.toActionWindow);
					}
					done = true;
					break;
				}
			}

			if(!done && !cStr.trim().equals(""))
			{
				gameManager.addToGameSummary("Command not found: " + cStr);
			}
		}

		if (lastCommands.size() == 0) { // Fallback to surface with no valid commands
			gameManager.addToGameSummary("No valid commands. Doing surface.");
			gameManager.addTooltip(this, "No valid command.");
			Command surface = getCommand(SurfaceCommand.NAME);
			surface.executeIfYouCan(SurfaceCommand.NAME);
			lastCommands.add(surface);
			cmds.add(surface.toActionWindow);
		}

		lastCommands.get(0).doGraphics();

		gridManager.updateAction(this, String.join(" -> ", cmds));

		messageText.setText(message);
		entityManager.commitEntityState(0, messageText);
	}

	public void doGraphics(int id) throws GameException{
		lastCommands.get(id).doGraphics();
	}
}