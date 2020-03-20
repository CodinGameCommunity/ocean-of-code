package com.codingame.game.commands;

import com.codingame.game.GameException;

public class MessageCommand extends Command {

    public static String NAME = "MSG";

    @Override
    public boolean executeIfYouCan(String command) throws GameException {
        if (!command.startsWith(NAME))
            return false;

        if(command.length() <= 4){
            return true; // ignore errors in msg..
        }

        player.message = command.replaceAll("\\|", " ").substring(4, command.length());
        if(player.message.length() > 26){
            player.message = player.message.substring(4, 25);
        }
        setSummary(null);
        setResult(null);
        return true;
    }

    @Override
    public boolean isValidCommand() {
        return false;
    }
}
