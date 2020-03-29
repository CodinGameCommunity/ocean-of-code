package com.codingame.game.model;

import com.codingame.game.GameException;
import com.codingame.gameengine.core.AbstractPlayer;

import java.awt.*;
import java.util.Random;

public class Game {
    public PlayerModel[] players;
    private IPlayerManager playerManager;
    private Random rand;
    private final int maxTurns = 598;

    public GridModel gridModel;
    public PlayerModel activePlayer;
    public int turn = 0;

    public Game(IPlayerManager playerManager, Random rand){
        this.playerManager = playerManager;
        this.rand = rand;
        gridModel = new GridModel();
        players = new PlayerModel[]{ new PlayerModel(0, this, gridModel), new PlayerModel(1, this, gridModel)};
        gridModel.initialize(rand);
    }

    public void initialize(int league){
        updateScore();
        playerManager.sendData(getInitialInput(players[0]), 0);
        playerManager.sendData(getInitialInput(players[1]), 1);
        initializePlayer(players[0], league);
        initializePlayer(players[1], league);
    }

    public void onRound(){
        if(isGameOver()){
            playerManager.endGame();
            return;
        }

        try{
            if(turn == 0){
                turn++;
                activePlayer = players[0];
                handleNewRound(activePlayer);
                updateScore();
                return;
            }

            if (activePlayer.hasMoreActions()){
                activePlayer.performRoundAction();
                updateScore();
                return;
            }

            turn++;
            if(turn > maxTurns){
                playerManager.endGame();
                return;
            }

            activePlayer = players[1-activePlayer.index];
            handleNewRound(activePlayer);
            updateScore();
        }
        catch (AbstractPlayer.TimeoutException e){
            playerManager.disablePlayer(activePlayer.index, "Timeout!");
            activePlayer.life=-1;
        }
        catch (GameException e){
            playerManager.disablePlayer(activePlayer.index, e.getMessage());
            activePlayer.life=-1;
        }
        catch (Exception e){
            playerManager.disablePlayer(activePlayer.index, "provided invalid input");
            activePlayer.life=-1;
        }
    }

    private void handleNewRound(PlayerModel playerModel) throws AbstractPlayer.TimeoutException, GameException {
        playerManager.sendData(getRoundInput(playerModel), playerModel.index);
        playerManager.execute(playerModel.index);
        playerModel.handleRoundInput(playerManager.getOutputs(playerModel.index));
    }

    private String[] getRoundInput(PlayerModel playerModel){
        PlayerModel opponent = playerModel.getOpponent();
        String[] input = new String[3];
        input[0] = (String.format("%d %d %d %d %s", playerModel.position.x, playerModel.position.y,
                playerModel.life, opponent.life, playerModel.getCooldowns()));
        input[1] = playerModel.sonarResult;
        input[2] = (String.format("%s", opponent.getSummaries()));
        return input;
    }

    private String[] getInitialInput(PlayerModel playerModel){
        boolean[][] grid = gridModel.grid;

        String[] input = new String[GridModel.GRID_SIZE + 1];
        input[0] = String.format("%d %d %d", GridModel.GRID_SIZE, GridModel.GRID_SIZE, playerModel.index);

        // send the grid
        for (int y = 0; y < GridModel.GRID_SIZE; y++) {
            StringBuffer line = new StringBuffer();
            for (int x = 0; x < GridModel.GRID_SIZE; x++) {
                line.append(grid[x][y] ? "x" : ".");
            }
            input[y + 1] = (line.toString());
        }

        return input;
    }

    private void initializePlayer(PlayerModel playerModel, int league){
        try
        {
            playerManager.execute(playerModel.index);
            playerModel.initialize(playerManager.getOutputs(playerModel.index), league);
        }
        catch (AbstractPlayer.TimeoutException e){
            playerManager.disablePlayer(playerModel.index, "Timeout!");
            playerModel.life=-1;
        }
        catch (GameException e){
            playerManager.disablePlayer(playerModel.index, e.getMessage());
            playerModel.life=-1;
        }
        catch (Exception e){
            playerManager.disablePlayer(playerModel.index, "provided invalid input");
            playerModel.life=-1;
        }
    }

    public void addTooltip(PlayerModel playerModel, String tooltip){
        playerManager.addTooltip(playerModel.index, tooltip);
    }

    public void addError(PlayerModel playerModel, String error){
        playerManager.addGameSummary(playerModel.index, error);
    }

    private void updateScore(){
        playerManager.updateScore(0, players[0].life);
        playerManager.updateScore(1, players[1].life);
    }

    private boolean isGameOver(){
        return players[0].life <= 0 || players[1].life <= 0 || turn > maxTurns;
    }

    public void explode(Point target){
        for(PlayerModel playerModel : players) {
            if (playerModel.position.equals(target)) {
                playerModel.reduceLife(2);
                continue;
            }

            if (Math.abs(playerModel.position.x - target.x) <= 1 && Math.abs(playerModel.position.y - target.y) <= 1) {
                playerModel.reduceLife(1);
            }
        }
    }
}
