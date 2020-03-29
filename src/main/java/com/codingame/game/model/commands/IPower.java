package com.codingame.game.model.commands;

import com.codingame.game.GameException;
import com.codingame.game.model.PlayerAction;

public interface IPower extends ICommand {
    int getMaxValue();
    int getCharges();
    int getPowerIndex();
    void increaseCharges();
}
