package com.codingame.game;

import com.codingame.game.model.Game;
import com.codingame.game.model.GridModel;
import com.codingame.game.model.Mine;
import com.codingame.game.model.PlayerModel;
import com.codingame.game.model.commands.*;
import com.codingame.gameengine.module.entities.*;
import com.codingame.gameengine.module.entities.Rectangle;
import com.codingame.gameengine.module.tooltip.TooltipModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewController {
    private static final int CELL_WIDTH = 60;
    public static final int GRID_SIZE = 15;
    private static final int GRID_ORIGIN_Y = (int) Math.round(1080 / 2 - CELL_WIDTH * GRID_SIZE / 2);
    private static final int GRID_ORIGIN_X = (int) Math.round(1920 / 2 - CELL_WIDTH * GRID_SIZE / 2);

    private Referee referee;
    private Game game;
    private GraphicEntityModule entityManager;
    private ArrayList<Player> players;
    private TooltipModule tooltipModule;
    private ArrayList<ViewPart> parts = new ArrayList<>();

    public ViewController(Referee referee, Game game, GraphicEntityModule entityManager, ArrayList<Player> players, TooltipModule tooltipModule){
        this.referee = referee;
        this.game = game;
        this.entityManager = entityManager;
        this.players = players;
        this.tooltipModule = tooltipModule;
    }

    public void preInitialize(){
        setupMap();
        parts.add(new ActionMessagePart(game));
        parts.add(new ExplosionPart());
        setupPlayer(game.players[0]);
        setupPlayer(game.players[1]);
    }

    public void initialize(){
        parts.add(new SubmarinePart(game.players[0]));
        parts.add(new SubmarinePart(game.players[1]));
        parts.add(new PathMapPart(game.players[0]));
        parts.add(new PathMapPart(game.players[1]));
        for(int i = 0; i < game.players[0].powers.size(); i++){
            parts.add(new PowerPart(game.players[0], players.get(0), i));
            parts.add(new PowerPart(game.players[1], players.get(1), i));
        }
    }

    public void onRound(){
        for(ViewPart part : parts){
            part.update();
        }
    }

    private int convertX(double unit) {
        return (int) (GRID_ORIGIN_X + unit * CELL_WIDTH);
    }

    private int convertY(double unit) {
        return (int) (GRID_ORIGIN_Y + unit * CELL_WIDTH);
    }

    private void setupMap(){
        boolean[][] grid = game.gridModel.grid;
        Map<Point, String> tiles = game.gridModel.tiles;
        for (Map.Entry<Point, String> entry : tiles.entrySet()) {
            Sprite sprite = entityManager.createSprite().setImage(entry.getValue())
                    .setBaseWidth(60)
                    .setBaseHeight(60)
                    .setX(convertX(entry.getKey().x))
                    .setY(convertY(entry.getKey().y));
            entityManager.commitEntityState(0, sprite);
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
    }

    public Circle createCircle(Point point, int color) {
        return entityManager.createCircle().setX(convertX(point.x) + CELL_WIDTH / 2)
                .setY(convertY(point.y) + CELL_WIDTH / 2).setRadius(CELL_WIDTH / 3).setFillColor(color)
                .setLineColor(color);
    }

    private void setupPlayer(PlayerModel playerModel){
        Player player = players.get(playerModel.index);
        parts.add(new MessagePart(playerModel));
        parts.add(new LifePart(playerModel, player));
        parts.add(new MinePart(playerModel));
        createNickname(player);
        createPlayerFrame(player);

    }

    private void createPlayerFrame(Player player){
        Sprite sprite = entityManager.createSprite()
                .setImage(player.getAvatarToken()).setAnchorX(0.5).setBaseHeight(200).setBaseWidth(200).setY(50).setX(1080/4);

        addFrame(1080/4-5-100, 45, 205, 205, 1, player);

        if (player.getIndex() == 1) {
            sprite.setX(1920-sprite.getX());
        }
        entityManager.commitEntityState(0, sprite);
    }

    private void addFrame(int x, int y, int width, int height, int z, Player player){
        Rectangle rect = entityManager.createRectangle()
                .setLineColor(player.getColorToken())
                .setLineWidth(10)
                .setZIndex(z)
                .setX(x)
                .setY(y)
                .setWidth(width)
                .setHeight(height)
                .setFillAlpha(0);

        if (player.getIndex() == 1) {
            rect.setX(1920-rect.getX()-rect.getWidth());
        }

        entityManager.commitEntityState(0, rect);
    }

    private void createNickname(Player player) {
        Text nickname = entityManager.createText(player.getNicknameToken())
                .setFontFamily("Arial")
                .setX(1080/4)
                .setY(270)
                .setZIndex(20)
                .setAnchorX(0.5)
                .setMaxWidth(400)
                .setFontSize(60)
                .setFillColor(player.getColorToken());

        if (player.getIndex() == 1) {
            nickname.setX(1920-nickname.getX());
        }
        entityManager.commitEntityState(0, nickname);
    }

    private void addExplosion(Point position, PlayerModel playerModel){
        Player player = players.get(playerModel.index);
        String file = "explosion";
        int animation = 5;
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
        rect.setAlpha(0, Curve.ELASTIC);

        Sprite entity = entityManager.createSprite().setImage(file + "/0.png")
                .setX(convertX(position.x) + CELL_WIDTH / 2).setY(convertY(position.y) + CELL_WIDTH / 2).setScale(1.5)
                .setAnchor(0.5);

        for (int i = 0; i < animation; i++) {
            entity.setImage(file + "/" + i + ".png");
            entityManager.commitEntityState(i / (animation - 1.0), entity);
        }

        entity.setScale(0.5);
    }

    public Line createLine(Point p1, Point p2, int color) {
        return entityManager.createLine().setX(convertX(p1.x) + CELL_WIDTH / 2).setY(convertY(p1.y) + CELL_WIDTH / 2)
                .setX2(convertX(p2.x) + CELL_WIDTH / 2).setY2(convertY(p2.y) + CELL_WIDTH / 2).setLineColor(color)
                .setLineWidth(3);
    }


    public abstract class ViewPart {
        public abstract void update();
    }

    public class ExplosionPart extends ViewPart{
        @Override
        public void update() {
            if(game.activePlayer != null && game.activePlayer.lastCommand != null && game.activePlayer.lastCommand.activator.equals(TorpedoPower.NAME)){
                String[] coords = game.activePlayer.lastCommand.command.split(" ");
                int x = Integer.parseInt(coords[1]);
                int y = Integer.parseInt(coords[2]);
                explode(new Point(x, y), game.activePlayer);
            }
        }

        private void explode(Point point, PlayerModel playerModel){
            Line line = createLine(playerModel.position, point, players.get(playerModel.index).getColorToken());
            entityManager.commitEntityState(0.2, line);
            line.setAlpha(0);
            entityManager.commitEntityState(1, line);
            addExplosion(point, playerModel);
        }
    }

    public class MinePart extends ViewPart{
        private PlayerModel playerModel;
        private Map<Mine, SpriteAnimation> mines = new HashMap<>();

        public MinePart(PlayerModel playerModel){
            this.playerModel = playerModel;
        }

        @Override
        public void update() {
            if(game.activePlayer != playerModel) return;
            for(Mine mine : playerModel.mines){
                if(!mine.isActive && !mines.containsKey(mine)){
                    addMine(mine);
                }else if(mine.isBlown && mines.containsKey(mine)){
                    mines.get(mine).setAlpha(0);
                    tooltipModule.setTooltipText(mines.get(mine), "");
                    entityManager.commitEntityState(0.4, mines.get(mine));
                    mines.remove(mine);
                    addExplosion(mine.point, playerModel);
                }
            }
        }

        private void addMine(Mine mine){
            SpriteAnimation mineAnimation = entityManager.createSpriteAnimation()
                    .setImages(new String[]{ "mine0.png", "mine1.png", "mine0.png", "mine2.png"})
                    .setZIndex(5)
                    .setAnchor(0.5)
                    .setTint(players.get(playerModel.index).getColorToken())
                    .setX(convertX(playerModel.position.x) + CELL_WIDTH / 2 )
                    .setY(convertY(playerModel.position.y) + CELL_WIDTH / 2)
                    .setDuration(1000)
                    .setLoop(true);

            mines.put(mine, mineAnimation);
            tooltipModule.setTooltipText(mineAnimation, "Mine\nOwner = " + playerModel.index);
            entityManager.commitEntityState(0, mineAnimation);
            mineAnimation.setX(convertX(mine.point.x)+ CELL_WIDTH / 2 - 10 + playerModel.index*20)
                    .setY(convertY(mine.point.y)+ CELL_WIDTH / 2 - 10 + playerModel.index*20)
                    .setZIndex(5);
            mineAnimation.setPlaying(true);
        }
    }

    public class PathMapPart extends ViewPart{
        private PlayerModel playerModel;
        private Entity<?>[][] grid = new Entity<?>[GridModel.GRID_SIZE][GridModel.GRID_SIZE];

        public PathMapPart(PlayerModel playerModel)
        {
            this.playerModel = playerModel;
            grid[playerModel.position.x][playerModel.position.y] = fillCell(playerModel.position, players.get(playerModel.index));
        }

        @Override
        public void update() {
            if(game.activePlayer != playerModel) return;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    Entity<?> path = grid[i][j];
                    if(path == null && playerModel.grid[i][j]){
                        grid[i][j] = fillCell(new Point(i, j), players.get(playerModel.index));
                        continue;
                    }
                    else if(path != null && playerModel.grid[i][j]){
                        continue;
                    }

                    if (path != null && !playerModel.grid[i][j]) {
                        path.setAlpha(0);
                    }

                    grid[i][j] = null;
                }
            }
        }

        public Entity fillCell(Point p, Player player) {
            return entityManager.createRectangle().setX(convertX(p.x)).setY(convertY(p.y)).setWidth(CELL_WIDTH).setHeight(CELL_WIDTH)
                    .setAlpha(0.5).setFillColor(player.getColorToken()).setLineAlpha(0).setZIndex(player.getIndex()*-1+1);
        }
    }

    public class SubmarinePart extends ViewPart{
        private PlayerModel playerModel;
        private Group group;
        private Sprite sub;

        public SubmarinePart(PlayerModel playerModel){
            this.playerModel = playerModel;
            sub = entityManager.createSprite().setImage("sub.png").setTint(players.get(playerModel.index).getColorToken()).setScale(1).setScaleY(1.5).setAnchor(0.5);
            group = entityManager.createGroup(sub).setZIndex(100)
                    .setX(convertX(playerModel.position.x) + CELL_WIDTH / 2)
                    .setY(convertY(playerModel.position.y) + CELL_WIDTH / 2);
            tooltipModule.setTooltipText(sub, "Player " + playerModel.index);
            entityManager.commitEntityState(0, group, sub);
        }

        @Override
        public void update() {
            if(game.activePlayer != playerModel) return;
            Sprite image = sub;
            handleSonar();
            handleSurface();

            int newX = convertX(playerModel.position.x) + CELL_WIDTH / 2;
            int newY = convertY(playerModel.position.y) + CELL_WIDTH / 2;

            double rot = image.getRotation();
            if (newX - group.getX() < 0) {
                image.setRotation(Math.PI);
            } else if (newX - group.getX() > 0) {
                image.setRotation(0);
            } else if (newY - group.getY() < 0) {
                image.setRotation(Math.PI/2*3);
            } else if (newY - group.getY() > 0) {
                image.setRotation(Math.PI/2);
            }else{
                handleSilence(); // 0 dist
                return;
            }

            if(Math.abs(rot-image.getRotation()) > 0.0001){
                entityManager.commitEntityState(0, image);
            }

            handleSilence();

            group.setX(newX).setY(newY);
        }

        private void handleSilence(){
            if(game.activePlayer == playerModel && playerModel.lastCommand != null && playerModel.lastCommand.activator.equals(SilencePower.NAME)){
                sub.setAlpha(0.7, Curve.LINEAR);
                entityManager.commitEntityState(0.5, sub);
                sub.setAlpha(1.0, Curve.LINEAR);
            }
        }
        private void handleSonar(){
            if(game.activePlayer == playerModel && playerModel.lastCommand != null && playerModel.lastCommand.activator.equals(SonarPower.NAME)){
                int grid = Integer.parseInt(playerModel.lastCommand.command.split(" ")[1]);
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
                        .setLineColor(players.get(playerModel.index).getColorToken())
                        .setLineWidth(6));

                Circle c2;
                sonar.add(c2 = entityManager.createCircle()
                        .setFillAlpha(0)
                        .setLineAlpha(1)
                        .setRadius(CELL_WIDTH*2)
                        .setLineColor(players.get(playerModel.index).getColorToken())
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
        }

        private void handleSurface(){
            Player player = players.get(playerModel.index);
            if(game.activePlayer == playerModel && playerModel.lastCommand != null && playerModel.lastCommand.command.equals(SurfaceCommand.NAME)){
                Circle circle = createCircle(playerModel.position, player.getColorToken());
                circle.setFillAlpha(0).setScale(0);

                entityManager.commitEntityState(0, circle);
                circle.setFillAlpha(0.5).setScale(1, Curve.ELASTIC);
                entityManager.commitEntityState(0.2, circle);

                circle.setScale(10);
                circle.setAlpha(0);
                entityManager.commitEntityState(1, circle);

                group.setScale(2);
                entityManager.commitEntityState(0.5, group);
                group.setScale(1);
                entityManager.commitEntityState(1, group);
            }
        }
    }

    public class LifePart extends ViewPart{
        private int lastLife;
        private PlayerModel playerModel;
        private ArrayList<Circle> graphicLife = new ArrayList<Circle>();

        public LifePart(PlayerModel playerModel, Player player){
            lastLife = playerModel.life;
            this.playerModel = playerModel;
            for (int i = 0; i < playerModel.life; i++) {

                int x = 1080/4+1080/6-10;
                int y = 35 + (playerModel.life-i-1) * 40;

                if (playerModel.index == 1) {
                    x = 1920 - x;
                }

                graphicLife.add(entityManager.createCircle().setX(x).setY(y).setFillColor(player.getColorToken()).setRadius(12)
                        .setAlpha(0.8).setLineColor(0x000000)
                        .setLineWidth(4));
                entityManager.commitEntityState(0, graphicLife.get(graphicLife.size()-1));
            }
        }

        @Override
        public void update() {
            for(int i = lastLife-1; i >= Math.max(0, playerModel.life); i--){
                graphicLife.get(i).setFillColor(0x555555).setScale(0.5);
            }

            lastLife = playerModel.life;
        }
    }

    public class PowerPart extends ViewPart{
        private int charges;
        private PlayerModel playerModel;
        private Player player;
        private IPower power;
        private ArrayList<Circle> graphicCharges = new ArrayList<Circle>();

        public PowerPart(PlayerModel playerModel, Player player, int index){
            this.playerModel = playerModel;
            this.player = player;
            power = playerModel.powers.get(index);

            int y = 550 + 120 * power.getPowerIndex();

            entityManager.commitEntityState(0, entityManager
                    .createText(power.getName())
                    .setFontFamily("Arial")
                    .setX(player.getIndex() == 1 ? 30 : 1890)
                    .setY(y-50)
                    .setAnchorX(player.getIndex() == 1? 0 : 1)
                    .setFontSize(30)
                    .setAnchorY(0.5)
                    .setZIndex(5)
                    .setFontWeight(Text.FontWeight.BOLD)
                    .setFillColor(0xefefef));

            for (int i = 0; i < power.getMaxValue(); i++) {

                int x = 50 + 55 * i;

                if (player.getIndex() == 1) {
                    x = 1920 - (50 + 55 * i);
                }

                graphicCharges.add(entityManager.createCircle()
                        .setX(x)
                        .setY(y)
                        .setFillColor(0x555555)
                        .setAlpha(0.8)
                        .setRadius(20)
                        .setScale(0.5)
                        .setLineWidth(5)
                        .setLineColor(0x000000));

                entityManager.commitEntityState(0, graphicCharges.get(graphicCharges.size()-1));
            }
        }

        @Override
        public void update() {
            if(game.activePlayer != playerModel) return;
           if(charges < power.getCharges()){
               for (int i = charges; i < power.getCharges(); i++) {
                   graphicCharges.get(i).setScale(1.0, Curve.ELASTIC).setFillColor(player.getColorToken());
               }
           }else if(charges > power.getCharges()){
               for (int i = charges-1; i >= power.getCharges(); i--) {
                   graphicCharges.get(i).setScale(0.5, Curve.ELASTIC).setFillColor(0x555555);
               }
           }

           charges = power.getCharges();
        }
    }

    public class MessagePart extends ViewPart{
        private PlayerModel playerModel;
        private Text messageText;

        public MessagePart(PlayerModel playerModel){
            this.playerModel = playerModel;
            createMessageText();
        }

        private void createMessageText(){
            messageText = entityManager.createText("")
                    .setFontFamily("Arial")
                    .setFillColor(0xffffff)
                    .setX(playerModel.index==0?30:1890)
                    .setMaxWidth(400)
                    .setY(370)
                    .setFontSize(30)
                    .setZIndex(100);

            if (playerModel.index == 1) {
                messageText.setAnchorX(1);
            }
        }
        @Override
        public void update() {
            if(!messageText.getText().equals(playerModel.message)){
                messageText.setText(playerModel.message);
                entityManager.commitEntityState(0, messageText);
            }
        }
    }

    public class ActionMessagePart extends ViewPart{
        private Text actionText;
        private Game game;

        public ActionMessagePart(Game game){
            this.game = game;

            actionText = entityManager.createText("")
                    .setX(1920 / 2)
                    .setAnchor(0.5)
                    .setStrokeColor(0x000000)
                    .setStrokeThickness(2)
                    .setY(1040)
                    .setFontFamily("Arial")
                    .setFontSize(40);
        }
        @Override
        public void update() {
            actionText.setText(game.activePlayer.actionMessage).setFillColor(players.get(game.activePlayer.index).getColorToken());

            entityManager.commitEntityState(0, actionText);
        }
    }
}
