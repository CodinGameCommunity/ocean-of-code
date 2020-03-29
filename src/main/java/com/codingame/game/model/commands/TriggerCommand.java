package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.Mine;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;

import java.awt.*;

public class TriggerCommand implements ICommand {

    public static String NAME = "TRIGGER";
    private PlayerModel playerModel;

    public TriggerCommand(PlayerModel playerModel){
        this.playerModel = playerModel;
    }

    @Override
    public String getName() { return NAME; }

    @Override
    public boolean canExecute(PlayerAction action) throws GameException {
        String[] commands = action.command.split(" ");
        if(commands.length > 3){
            throw new GameException("Invalid amount of params for TRIGGER, should be TRIGGER x y");
        }

        Point target = getTarget(action.command);
        for(Mine mine : playerModel.mines){
            if (mine.isBlown) continue;
            if (!mine.isActive) continue;
            if (mine.point.equals(target)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void execute(PlayerAction action) throws GameException {
        playerModel.game.addTooltip(playerModel, action.command);
        Point target = getTarget(action.command);
        for(Mine mine : playerModel.mines){
            if (mine.isBlown) continue;
            if (!mine.isActive) continue;
            if (!mine.point.equals(target)) continue;
            mine.isBlown = true;
            playerModel.game.explode(target);
            playerModel.summaries.add(String.format("%s %d %d", NAME, target.x, target.y));
            return;
        }
    }

    @Override
    public String getActionWindowMessage(PlayerAction action) throws GameException {
        return action.command;
    }

    private Point getTarget(String command) throws GameException{
        try{
            String[] commands = command.split(" ");
            Point target = new Point(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
            return target;
        }
        catch (Exception e){
            throw new GameException("Invalid params to Trigger. Should be Trigger x y");
        }
    }
}
