package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

public class MessageCommand implements ICommand {
    public static String NAME = "MSG";
    private PlayerModel playerModel;

    public MessageCommand(PlayerModel playerModel){
        this.playerModel = playerModel;
    }

    public String getName() { return NAME; }

    public boolean canExecute(PlayerAction action) throws GameException {
        return action.activator.equals(NAME);
    }

    public void execute(PlayerAction action) {
        if(action.command.length() < 5){
            return;
        }

        playerModel.message = action.command.substring(4, action.command.length());
    }

    public String getActionWindowMessage(PlayerAction action) {
        return null;
    }
}
