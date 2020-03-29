package com.codingame.game.model;

import com.codingame.gameengine.core.AbstractPlayer;

public interface IPlayerManager {
    String getOutputs(int playerId) throws AbstractPlayer.TimeoutException;
    void sendData(String[] lines, int playerId);
    void execute(int playerId);
    void updateScore(int playerId, int score);
    void disablePlayer(int playerId, String reason);
    void endGame();
    void addTooltip(int playerId, String message);
    void addGameSummary(int playerId, String message);
}
