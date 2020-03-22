package com.codingame.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GridManager {
	
	private static final int CELL_WIDTH = 60;
	public static final int GRID_SIZE = 15;
	private static final int GRID_ORIGIN_Y = (int) Math.round(1080 / 2 - CELL_WIDTH * GRID_SIZE / 2);
	private static final int GRID_ORIGIN_X = (int) Math.round(1920 / 2 - CELL_WIDTH * GRID_SIZE / 2);
	private Map<Player, Group> players = new HashMap<Player, Group>();
	private Map<Player, Sprite> playerSprites = new HashMap<Player, Sprite>();
	private Map<Mine, SpriteAnimation> mines = new HashMap<>();
	private boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
	private GameManager gameManager;
	private Text actionText;

	public GridManager() {

	}

	public boolean isEmpty(Point p) {
		return p.x>=0 && p.x<GRID_SIZE && p.y>=0 && p.y<GRID_SIZE && !grid[p.x][p.y];
	}
	
	public void init(Random rand, TooltipModule tooltipModule, GameManager gameManager) {
		this.gameManager = gameManager;
		boolean[][] result = new boolean[GRID_SIZE][GRID_SIZE];

		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				result[x][y] = false;
			}
		}

		// create random islands
		for (int i = 0; i < rand.nextInt(26) + 5; i++) {

			int x = rand.nextInt(15);
			int y = rand.nextInt(15);

			result[x][y] = true;
		}

		grid = Utils.copyGrid(result);

		// increase the island tiles
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				if (result[x][y]) {
					if (y - 1 >= 0) {
						grid[x][y - 1] = true;
					}

					if (x + 1 < GRID_SIZE) {
						grid[x + 1][y] = true;

						if (y - 1 >= 0) {
							grid[x + 1][y - 1] = true;
						}
					}
				}
			}
		}

		Map<Point, String> tiles = null;

		do {
			tiles = checkError(grid);
		} while (tiles == null);

		for (Map.Entry<Point, String> entry : tiles.entrySet()) {
			Sprite sprite = entityManager.createSprite().setImage(entry.getValue())
					.setBaseWidth(60)
					.setBaseHeight(60)
					.setX(convertX(entry.getKey().x))
					.setY(convertY(entry.getKey().y));
			tooltipModule.setTooltipText(sprite, "Island\nx = " + (int) entry.getKey().getX() + "\ny = " + (int) entry.getKey().getY());
		}

		// set tooltips for water tiles
		for (int x = 0; x < GRID_SIZE; x++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				if (!grid[x][y]) {
					Rectangle rectangle = entityManager.createRectangle().setX(convertX(x)).setY(convertY(y)).setWidth(CELL_WIDTH).setHeight(CELL_WIDTH).setAlpha(0);
					tooltipModule.setTooltipText(rectangle, "Water\nx = " + x + "\ny = " + y);
				}
			}
		}

		actionText = entityManager.createText("")
				.setX(1920 / 2)
				.setAnchor(0.5)
				.setStrokeColor(0x000000)
				.setStrokeThickness(2)
				.setY(1040)
				.setFontFamily("Arial")
				.setFontSize(40);
	}

	public void surface(Player player){
		Circle circle = createCircle(player.getPosition(), player.getColorToken());
		circle.setFillAlpha(0);

		entityManager.commitEntityState(0, circle);
		circle.setFillAlpha(0.5);
		entityManager.commitEntityState(0.2, circle);

		circle.setScale(10);
		circle.setAlpha(0);
		entityManager.commitEntityState(1, circle);

		Group submarine = getPlayers().get(player);
		submarine.setScale(2);
		entityManager.commitEntityState(0.5, submarine);
		submarine.setScale(1);
		entityManager.commitEntityState(1, submarine);
	}

	public void torpedo(Point target, Player player, Point origin){
		Line line = createLine(origin, target, player.getColorToken());
		entityManager.commitEntityState(0.2, line);
		line.setAlpha(0);
		entityManager.commitEntityState(1, line);
		createTargetExplosion("explosion", 5, target, player);
	}

	private Map<Point, String> checkError(boolean[][] grid) {
		String[] tiles = new String[] { "11.0.111", ".11111.0", ".0.0.111", "11.0.0.1", ".111.0.0", ".0.111.0",
				"1111.0.1", ".0.11111", "11111111", "11111101", "11110111", "11011111", "01111111" };

		Map<Point, String> tileSprites = new HashMap<Point, String>();

		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				boolean island = grid[x][y];

				if (!island)
					continue;

				// create map
				StringBuilder map = new StringBuilder();
				map.append(y - 1 < 0 || x - 1 < 0 || grid[x - 1][y - 1] ? "1" : "0");
				map.append(y - 1 < 0 || grid[x][y - 1] ? "1" : "0");
				map.append(y - 1 < 0 || x + 1 >= GRID_SIZE || grid[x + 1][y - 1] ? "1" : "0");
				map.append(x + 1 >= GRID_SIZE || grid[x + 1][y] ? "1" : "0");
				map.append(y + 1 >= GRID_SIZE || x + 1 >= GRID_SIZE || grid[x + 1][y + 1] ? "1" : "0");
				map.append(y + 1 >= GRID_SIZE || grid[x][y + 1] ? "1" : "0");
				map.append(y + 1 >= GRID_SIZE || x - 1 < 0 || grid[x - 1][y + 1] ? "1" : "0");
				map.append(x - 1 < 0 || grid[x - 1][y] ? "1" : "0");

				String sprite = null;
				for (int i = 0; i < tiles.length; i++) {
					if (map.toString().matches(tiles[i])) {
						sprite = String.format("island/%s.png", tiles[i].replaceAll("\\.", "x"));
						tileSprites.put(new Point(x, y), sprite);
						break;
					}
				}

				if (sprite == null) {
					// fix error
					grid[Math.max(x - 1, 0)][Math.max(0, y - 1)] = true;
					grid[x][Math.max(0, y - 1)] = true;
					grid[Math.min(x + 1, GRID_SIZE - 1)][Math.max(0, y - 1)] = true;
					grid[Math.max(x - 1, 0)][Math.max(0, y)] = true;
					grid[Math.min(x + 1, GRID_SIZE - 1)][Math.max(0, y)] = true;
					grid[Math.max(x - 1, 0)][Math.min(y + 1, GRID_SIZE - 1)] = true;
					grid[x][Math.min(y + 1, GRID_SIZE - 1)] = true;
					grid[Math.min(x + 1, GRID_SIZE - 1)][Math.min(y + 1, GRID_SIZE - 1)] = true;

					return null;
				}
			}
		}

		return tileSprites;
	}

	public boolean[][] getGrid() {
		return grid;
	}

	public Map<Player, Group> getPlayers() {
		return players;
	}

	@Inject
	private GraphicEntityModule entityManager;
	@Inject
	private TooltipModule tooltipModule;

	private int convertX(double unit) {
		return (int) (GRID_ORIGIN_X + unit * CELL_WIDTH);
	}

	private int convertY(double unit) {
		return (int) (GRID_ORIGIN_Y + unit * CELL_WIDTH);
	}


	public void updateAction(Player player, String actions){
		actionText.setText(actions)
				.setFillColor(player.getColorToken());

		entityManager.commitEntityState(0, actionText);
	}
	public Entity fillCell(Point p, int color, Player player) {
		//int x = convertX(p.x);
		//int y = convertY(p.y);
		//if(player.getIndex()==0){
		//	return entityManager.createPolygon()
		//			.addPoint(x+ CELL_WIDTH-CELL_WIDTH/2, y)
		//			.addPoint(x+CELL_WIDTH, y)
		//			.addPoint(x+CELL_WIDTH, y +CELL_WIDTH/2)
		//			.setFillAlpha(0.9)
		//			.setFillColor(color);
		//}
//
		//return entityManager.createPolygon()
		//		.addPoint(x, y + CELL_WIDTH-CELL_WIDTH/2)
		//		.addPoint(x+CELL_WIDTH/2, y+CELL_WIDTH)
		//		.addPoint(x, y +CELL_WIDTH)
		//		.setFillAlpha(0.9)
		//		.setFillColor(color);
		return entityManager.createRectangle().setX(convertX(p.x)).setY(convertY(p.y)).setWidth(CELL_WIDTH).setHeight(CELL_WIDTH)
				.setAlpha(0.5).setFillColor(color).setLineAlpha(0).setZIndex(player.getIndex()*-1+1);
	}

	private Random rnd = new Random();
	public void addMine(Mine mine, Point origin){
		int x = CELL_WIDTH / 2 - 2 + mine.player.getIndex()*4;
		int y = CELL_WIDTH / 2 - 2 + mine.player.getIndex()*4;
		SpriteAnimation circle = entityManager.createSpriteAnimation()
				.setImages(new String[]{ "mine0.png", "mine1.png", "mine0.png", "mine2.png"})
				.setZIndex(5)
				.setAnchor(0.5)
				.setTint(mine.player.getColorToken())
				.setX(convertX(origin.x) + CELL_WIDTH / 2 )
				.setY(convertY(origin.y) + CELL_WIDTH / 2)
				.setDuration(1000)
				.setLoop(true);

		mines.put(mine, circle);
		tooltipModule.setTooltipText(circle, "Mine\nOwner = " + mine.player.getIndex());
		entityManager.commitEntityState(0, circle);
		circle.setX(convertX(mine.point.x)+ CELL_WIDTH / 2 - 10 + mine.player.getIndex()*20)
				.setY(convertY(mine.point.y)+ CELL_WIDTH / 2 - 10 + mine.player.getIndex()*20)
				.setZIndex(5);
		circle.setPlaying(true);
	}

	public void addSonar(int grid, Player player){
		grid--;
		int x = grid % 3 * 5 + 2;
		int y = grid / 3 * 5 + 2;
		Group sonar = entityManager.createGroup()
				.setX(convertX(x) + CELL_WIDTH / 2)
				.setY(convertY(y) + CELL_WIDTH / 2)
				.setZIndex(1000);

		Circle c1;
		sonar.add(c1 = entityManager.createCircle()
				.setFillAlpha(0)
				.setLineAlpha(1)
				.setRadius(CELL_WIDTH*3)
				.setLineColor(player.getColorToken())
				.setLineWidth(6));

		Circle c2;
		sonar.add(c2 = entityManager.createCircle()
				.setFillAlpha(0)
				.setLineAlpha(1)
				.setRadius(CELL_WIDTH*2)
				.setLineColor(player.getColorToken())
				.setLineWidth(6));

		c2.setScale(0);
		c1.setScale(0);
		entityManager.commitEntityState(0, c1, c2, sonar);
		c2.setScale(1, Curve.EASE_OUT);
		c1.setScale(1, Curve.EASE_OUT);
		entityManager.commitEntityState(0.3, c1, c2);
		c2.setScale(0.7, Curve.EASE_IN);
		c1.setScale(0.7, Curve.EASE_IN);
		entityManager.commitEntityState(0.6, c1, c2);
		c2.setScale(1.3, Curve.EASE_OUT);
		c1.setScale(1.3, Curve.EASE_OUT);
		entityManager.commitEntityState(0.8, c1, c2, sonar);
		//sonar.setScale(0, Curve.EASE_IN_AND_OUT);
		sonar.setAlpha(0, Curve.ELASTIC);
		c2.setScale(0, Curve.EASE_IN_AND_OUT);
		c1.setScale(0, Curve.EASE_IN_AND_OUT);
	}

	public void beforeRound(){
		for(Map.Entry<Mine, SpriteAnimation> entry : mines.entrySet()) {
			entry.getKey().isActive = true;
		}
	}

	public boolean hasMine(Point point, Player player){
		for(Map.Entry<Mine, SpriteAnimation> entry : mines.entrySet()){
			if(!entry.getKey().point.equals(point) || !entry.getKey().player.equals(player)){
				continue;
			}

			if(!entry.getKey().isActive){
				continue;
			}

			return true;
		}

		return false;
	}

	public boolean explodeMine(Point point, Player player) throws GameException{
		for(Map.Entry<Mine, SpriteAnimation> entry : mines.entrySet()){
			if(!entry.getKey().point.equals(point) || !entry.getKey().player.equals(player)){
				continue;
			}

			if(!entry.getKey().isActive){
				continue;
			}

			blowMine(entry.getKey());
			return true;
		}

		return false;
	}

	private void blowMine(Mine mine){
		mines.get(mine).setAlpha(0);
		tooltipModule.setTooltipText(mines.get(mine), "");
		entityManager.commitEntityState(0.4, mines.get(mine));
		mines.remove(mine);
		createTargetExplosion("explosion", 5, mine.point, mine.player);
		for(Map.Entry<Player, Sprite> pentry : playerSprites.entrySet()){
			Point point = pentry.getKey().getPosition();
			if (Math.abs(point.x - mine.point.x) <= 1 && Math.abs(point.y - mine.point.y) <= 1) {
				pentry.getKey().reduceLife();
			}

			if (point.equals(mine.point)) {
				pentry.getKey().reduceLife();
			}
		}
	}

	public Circle createCircle(Point point, int color) {
		return entityManager.createCircle().setX(convertX(point.x) + CELL_WIDTH / 2)
				.setY(convertY(point.y) + CELL_WIDTH / 2).setRadius(CELL_WIDTH / 3).setFillColor(color)
				.setLineColor(color);
	}

	public Sprite createTargetExplosion(String file, int animation, Point position, Player player) {

		Rectangle rect = entityManager.createRectangle()
				.setWidth(CELL_WIDTH*3)
				.setHeight(CELL_WIDTH*3)
				.setX(convertX(position.x)-CELL_WIDTH)
				.setY(convertY(position.y)-CELL_WIDTH)
				.setAlpha(0)
				.setFillColor(player.getColorToken())
				.setLineAlpha(0)
				;

		entityManager.commitEntityState(0, rect);
		rect.setAlpha(0.3, Curve.ELASTIC);
		entityManager.commitEntityState(0.2, rect);
		rect.setAlpha(0);

		Sprite entity = entityManager.createSprite().setImage(file + "/0.png")
				.setX(convertX(position.x) + CELL_WIDTH / 2).setY(convertY(position.y) + CELL_WIDTH / 2).setScale(1.5)
				.setAnchor(0.5);

		for (int i = 0; i < animation; i++) {
			entity.setImage(file + "/" + i + ".png");
			entityManager.commitEntityState(i / (animation - 1.0), entity);
		}

		entity.setScale(0.5);
		return entity;
	}

	public Sprite createImage(String file, Point position) {
		Sprite entity = entityManager.createSprite().setImage(file).setX(convertX(position.x) + CELL_WIDTH / 2)
				.setY(convertY(position.y) + CELL_WIDTH / 2).setScale(1.5).setAnchor(0.5);

		return entity;
	}

	public Line createLine(Point p1, Point p2, int color) {
		return entityManager.createLine().setX(convertX(p1.x) + CELL_WIDTH / 2).setY(convertY(p1.y) + CELL_WIDTH / 2)
				.setX2(convertX(p2.x) + CELL_WIDTH / 2).setY2(convertY(p2.y) + CELL_WIDTH / 2).setLineColor(color)
				.setLineWidth(3);
	}

	public void createPlayer(Player player, TooltipModule tooltipModule) {
		Sprite sprite = entityManager.createSprite().setImage("sub.png").setTint(player.getColorToken()).setScale(1).setScaleY(1.5).setAnchor(0.5);
		playerSprites.put(player, sprite);
		players.put(player, entityManager.createGroup(sprite).setZIndex(100)
				.setX(convertX(player.getPosition().x) + CELL_WIDTH / 2)
				.setY(convertY(player.getPosition().y) + CELL_WIDTH / 2));
		tooltipModule.setTooltipText(sprite, "Player " + player.getIndex());
	}

	public void updatePlayerPosition(Player p, boolean silenced) {
		Point position = p.getPosition();

		Group group = players.get(p);
		Sprite image = playerSprites.get(p);

		int newX = convertX(position.x) + CELL_WIDTH / 2;
		int newY = convertY(position.y) + CELL_WIDTH / 2;

		double rot = image.getRotation();
		if (newX - group.getX() < 0) {
			image.setRotation(Math.PI);
		} else if (newX - group.getX() > 0) {
			image.setRotation(0);
		} else if (newY - group.getY() < 0) {
			image.setRotation(Math.PI/2*3);
		} else if (newY - group.getY() > 0) {
			image.setRotation(Math.PI/2);
		}

		if(Math.abs(rot-image.getRotation()) > 0.0001){
			entityManager.commitEntityState(0, image);
		}

		if(silenced){
			image.setAlpha(0.7, Curve.LINEAR);
			entityManager.commitEntityState(0.5, image);
			image.setAlpha(1, Curve.LINEAR);
		}

		group.setX(newX).setY(newY);
	}
}
