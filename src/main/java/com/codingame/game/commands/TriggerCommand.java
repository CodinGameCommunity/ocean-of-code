package com.codingame.game.commands;

import com.codingame.game.GameException;

import java.awt.*;

public class TriggerCommand extends Command {

    public static String NAME = "TRIGGER";

    @Override
    public boolean executeIfYouCan(String command) throws GameException {
        if (!command.startsWith(NAME+" "))
            return false;

        wasValid = true;

        String[] commands = command.split(" ");
        try{
            Point target = new Point(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
            setSummary(String.format("%s %d %d", NAME, target.x, target.y));
            if(!gridManager.hasMine(target, player)){
                wasValid = false;
                gameManager.addToGameSummary("No mine to trigger on " + target.x + " " + target.y);
                return true;
            }

            toActionWindow = getSummary();
        }catch (Exception e){
            wasValid = false;

            return true;
        }

        //gameManager.addTooltip(player, "Trigger");
        return true;
    }

    @Override
    public void doGraphics() throws GameException {
        String[] commands = toActionWindow.split(" ");
        Point target = new Point(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));

        gridManager.explodeMine(target, player);
    }
}
