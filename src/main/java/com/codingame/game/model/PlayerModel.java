package com.codingame.game.model;

import com.codingame.game.GameException;
import com.codingame.game.model.commands.*;

import java.awt.*;
import java.util.ArrayList;

public class PlayerModel {
    private MessageCommand messageCommand = new MessageCommand(this);
    private final PlayerAction fallbackAction = new PlayerAction("SURFACE", "SURFACE");
    private ArrayList<PlayerAction> actions = new ArrayList<>();

    public ArrayList<IPower> powers = new ArrayList<>();
    public ArrayList<ICommand> commands = new ArrayList<>();
    public Game game;
    public GridModel gridModel;
    public int life = 6;
    public int index;
    public Point position;
    public ArrayList<Mine> mines = new ArrayList<>();
    public String message = "";
    public boolean[][] grid = new boolean[GridModel.GRID_SIZE][GridModel.GRID_SIZE];
    public ArrayList<String> summaries = new ArrayList<>();
    public String sonarResult = "NA";
    public PlayerAction lastCommand;
    public String actionMessage = "";

    public PlayerModel(int index, Game game, GridModel gridModel){
        this.index = index;
        this.game = game;
        this.gridModel = gridModel;
    }

    public int getSector() {
        return (int) (Math.ceil((position.x + 1) / 5.0) + Math.floor(position.y / 5.0) * 3);
    }

    public void initialize(String output, int league) throws GameException {
        if (!output.matches("^[0-9]{1,2} [0-9]{1,2}$"))
            throw new GameException("Cannot read initial coordinates. Expected format: 'X Y'");
        String[] coordinates = output.split(" ");
        position = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

        if (!gridModel.isEmpty(position))
            throw new GameException("Invalid coordinates!");
        grid[position.x][position.y] = true;

        commands.add(new MoveCommand(this));
        commands.add(new SurfaceCommand(this));
        addPower(new TorpedoPower(this));

        if (league >= 2) {
            addPower(new SilencePower(this));
            addPower(new SonarPower(this));
        }

        if (league >= 3) {
            addPower(new MinePower(this));
            commands.add(new TriggerCommand(this));
        }
    }

    private void addPower(IPower power){
        commands.add(power);
        powers.add(power);
    }

    public boolean hasMoreActions(){
        return actions.size() > 0;
    }

    public void handleRoundInput(String input) throws GameException{
        updateMines();
        lastCommand = null;
        sonarResult = "NA";
        summaries.clear();
        actions = PlayerAction.cleanActions(input);
        ArrayList<String> actionMessages = new ArrayList<>();
        for(PlayerAction action : actions){
            if(messageCommand.canExecute(action)) messageCommand.execute(action);
            else{
                actionMessages.add(action.command);
            }
        }
        actionMessage = String.join(" > ", actionMessages);

        if(!hasValidCommandAndRemoveInvalid()){
            actions.add(fallbackAction);
        }

        performRoundAction();
    }

    public void performRoundAction() throws GameException{
        PlayerAction action = actions.get(0);
        lastCommand = action;
        actions.remove(0);
        for(ICommand command : commands){
            if(!command.getName().equals(action.activator)) continue;
            command.execute(action);
            break;
        }

        hasValidCommandAndRemoveInvalid(); // clear trailing invalid commands on the right player if at the end.
    }

    private boolean hasValidCommandAndRemoveInvalid() throws GameException{
        while(actions.size() > 0){
            PlayerAction action = actions.get(0);
            for(ICommand command : commands){
                if(!command.getName().equals(action.activator)) continue;
                if(!command.canExecute(action)){
                    actions.remove(0);
                    break;
                }else{
                    return true;
                }
            }
            if(messageCommand.canExecute(action)){
                messageCommand.execute(action);
                actions.remove(0);
            }else if(actions.contains(action)){
                throw new GameException("Unknown command: " + action.command);
            }
        }

        return false;
    }

    public PlayerModel getOpponent(){  return game.players[1-index]; }

    public void surface(){
        grid = new boolean[GridModel.GRID_SIZE][GridModel.GRID_SIZE];
        grid[position.x][position.y] = true;
        reduceLife(1);
    }

    public void reduceLife(int amount){
        life-=amount;
        if(life < 0){
            life = 0;
        }

        if(amount > 1)
            game.addTooltip(this, " lost " + amount + " lives");
        else
            game.addTooltip(this, " lost a life");
    }

    public void move(Direction direction) throws GameException {
        int x = position.x + direction.Dx;
        int y = position.y + direction.Dy;
        if(!gridModel.isEmpty(new Point(x, y))){
            throw new GameException("Invalid move. Tried to move outside map or onto island");
        }
        if(grid[x][y]){
            throw new GameException("Can't visit same location twice. Use surface.");
        }

        position = new Point(x, y);
        grid[x][y] = true;
    }

    public String getCooldowns(){
        return getCooldown(TorpedoPower.NAME)
                + " " + getCooldown(SonarPower.NAME)
                + " " + getCooldown(SilencePower.NAME)
                + " " + getCooldown(MinePower.NAME);
    }

    public int getCooldown(String commandName){
        for(IPower power : powers){
            if(power.getName().equals(commandName)){
                return power.getMaxValue() - power.getCharges();
            }
        }

        return -1;
    }

    public boolean hasPower(String commandName){
        for(IPower power : powers){
            if(power.getName().equals(commandName)){
                return true;
            }
        }

        return false;
    }

    public void chargePower(String name){
        for(IPower power : powers){
            if(power.getName().equals(name)){
                if(power.getMaxValue() > power.getCharges()){
                    power.increaseCharges();
                }
                return;
            }
        }
    }

    private void updateMines(){
        for(int i = mines.size()-1; i >= 0; i--){
            if(mines.get(i).isBlown){
                mines.remove(i);
            }
        }

        for(Mine mine : mines){
            mine.isActive = true;
        }
    }

    public String getSummaries() {
        return summaries.size() > 0 ? String.join("|", summaries) : "NA";
    }
}
