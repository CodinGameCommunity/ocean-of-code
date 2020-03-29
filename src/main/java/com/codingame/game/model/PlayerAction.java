package com.codingame.game.model;

import com.codingame.game.GameException;
import com.codingame.game.model.commands.MessageCommand;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerAction {
    public String activator;
    public String command;

    public PlayerAction(String activator, String command){
        this.activator = activator;
        this.command = command;
    }

    public static ArrayList<PlayerAction> cleanActions(String command) throws GameException {
        String[] commandStr = command.split("\\|");
        HashSet<String> keys = new HashSet<>();
        ArrayList<PlayerAction> actions = new ArrayList<>();
        for(int i = 0;i < commandStr.length; i++){
            String cStr = commandStr[i].trim();
            if(cStr.equals("")) continue;
            int activatorSize = cStr.indexOf(" ");
            if(activatorSize < 0){
                activatorSize = cStr.length();
            }
            String activator = cStr.substring(0, activatorSize);
            if(!keys.add(activator) ){
                if(!activator.equals(MessageCommand.NAME)){
                    throw new GameException("Two equal commands executed.");
                }
            }

            actions.add(new PlayerAction(activator, cStr));
        }

        return actions;
    }
}
