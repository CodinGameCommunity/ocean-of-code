package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.PlayerAction;

public interface ICommand {
    String getName();
    boolean canExecute(PlayerAction action) throws GameException;
    void execute(PlayerAction action) throws GameException;
    String getActionWindowMessage(PlayerAction action) throws GameException;
}
