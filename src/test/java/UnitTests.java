import com.codingame.game.*;
import com.codingame.game.Player;
import com.codingame.game.model.Game;
import com.codingame.game.model.IPlayerManager;
import com.codingame.game.model.PlayerAction;
import com.codingame.game.model.PlayerModel;
import com.codingame.game.model.commands.IPower;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

import java.awt.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UnitTests {
    // Verification of different aspects of the game.
    private Game game;
    private PlayerModel p1, p2;
    private TestPlayerManager playerManager;
    public static void main(String[] args) {
        try {
            Class c = UnitTests.class;
            Method[] m = c.getDeclaredMethods();
            int testCount = 0;
            int failCount = 0;
            for (int i = 0; i < m.length; i++){
                if(m[i].getName().contains("Test")){
                    UnitTests testClass = new UnitTests();
                    testClass.resetGameWorld();
                    boolean result = false;
                    try{
                        result = (boolean)m[i].invoke(testClass);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(!result){
                        System.err.println((result ? "PASSED" : "FAILED") + " " + m[i].getName());
                    }

                    if(result) testCount++;
                    else failCount++;
                }
            }

            System.err.println("------------------------------------");
            System.err.println("Total tests: " + (testCount+failCount) + " Failed: " + failCount);

        } catch (Exception e) {
            System.err.println("TESTRUN failed: " + e.getMessage());
        }
    }


    private void resetGameWorld() {
        playerManager = new TestPlayerManager();
        game = new Game(playerManager, new Random(-430391657L));
        p1 = game.players[0];
        p2 = game.players[1];
    }

    private void initializeLeague(int league){
        initializePosition(league, new Point(7,7));
    }

    private void initializePosition(int league, Point point){
        playerManager.setOutput(0, point.x +" " + point.y);
        playerManager.setOutput(1, point.x + " " + point.y);
        game.initialize(league);
    }

    private void doSeriesOfEqualMoves(String move, int rounds){
        playerManager.setOutput(0, move);
        playerManager.setOutput(1, move);
        for(int i = 0; i < rounds; i++){
            game.onRound();
        }
    }

    // TESTS
    public boolean chargesPowers_Test() throws GameException{
        initializeLeague(3);
        for(PlayerModel playerModel : game.players){
            for(IPower power : playerModel.powers){
                if(!assertValue(0, power.getCharges())
                    || !assertValue(false, power.canExecute(new PlayerAction(power.getName(), power.getName())))){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean bothOnInvalidLocations_BothDisabled_Test() throws GameException{
        initializePosition(3, new Point(-1, -1));

        return assertValue(-1, p1.life) &&
                assertValue(-1, p2.life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(true, playerManager.disabled[1]);

    }


    public boolean player0MovesFirst_Test() throws GameException{
        initializeLeague(3);
        playerManager.setOutput(0, "MOVE N");
        game.onRound();

        return assertValue(game.players[0].position, new Point(7, 6));
    }

    public boolean player1DoesNotMoveFirst_Test() throws GameException{
        initializeLeague(3);
        playerManager.setOutput(0, "MOVE N");
        game.onRound();

        return assertValue(new Point(7, 7), game.players[1].position);
    }

    public boolean EqualCommands_CrashPlayer_Test() throws GameException{
        try{
            initializeLeague(3);
            playerManager.setOutput(0, "MOVE N|MOVE S");
            game.onRound();
        }
        catch (Exception e){
        }

        return assertValue(true, playerManager.disabled[0]);
    }

    public boolean MoveS_Test(){
        initializeLeague(3);
        playerManager.setOutput(0, "MOVE S");
        game.onRound();

        return assertValue(game.players[0].position, new Point(7, 8));
    }

    public boolean MoveE_Test(){
        initializeLeague(3);
        playerManager.setOutput(0, "MOVE E");
        game.onRound();

        return assertValue(game.players[0].position, new Point(8, 7));
    }

    public boolean MoveW_Test(){
        initializeLeague(3);
        playerManager.setOutput(0, "MOVE W");
        game.onRound();

        return assertValue(game.players[0].position, new Point(6, 7));
    }

    public boolean MoveChargeTorpedo_Test(){
        initializeLeague(1);
        playerManager.setOutput(0, "MOVE W TORPEDO");
        game.onRound();

        return assertValue("2 -1 -1 -1", game.players[0].getCooldowns());
    }

    public boolean MoveChargeInvalid_Test(){
        initializeLeague(1);
        playerManager.setOutput(0, "MOVE W YOLO");
        game.onRound();

        return assertValue("3 -1 -1 -1", game.players[0].getCooldowns());
    }

    public boolean MoveWithoutCharge_Test(){
        initializeLeague(1);
        playerManager.setOutput(0, "MOVE W    ");
        game.onRound();

        return assertValue("3 -1 -1 -1", game.players[0].getCooldowns());
    }

    public boolean MoveOntoPath_Test(){
        initializeLeague(1);
        playerManager.setOutput(0, "MOVE N");
        game.onRound();
        playerManager.setOutput(1, "MOVE N");
        game.onRound();
        playerManager.setOutput(0, "MOVE S");
        game.onRound();

        return assertValue(-1, game.players[0].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean MoveTorpedo8Times_CorrectPosAndHealth_Test(){
        initializeLeague(1);
        doSeriesOfEqualMoves("MOVE N TORPEDO | TORPEDO 7 4", 8);

        if(!playerManager.summaries.get(0).contains("Not enough charges of TORPEDO")){
            return false;
        }

        return assertValue(2, game.players[0].life) &&
                assertValue(3, game.players[1].life) &&
                assertValue(new Point(7, 4), game.players[0].position) &&
                assertValue(new Point(7, 4), game.players[1].position);
    }

    public boolean MoveTorpedo9Times_CorrectPosAndHealth_Test(){
        initializeLeague(1);
        doSeriesOfEqualMoves("MOVE N TORPEDO | TORPEDO 7 4", 9);

        if(!playerManager.summaries.get(0).contains("Not enough charges of TORPEDO")){
            return false;
        }

        return assertValue(2, game.players[0].life) &&
                assertValue(3, game.players[1].life) &&
                assertValue(new Point(7, 3), game.players[0].position) &&
                assertValue(new Point(7, 4), game.players[1].position) &&
                assertValue("2 -1 -1 -1", game.players[0].getCooldowns()) &&
                assertValue("3 -1 -1 -1", game.players[1].getCooldowns());
    }

    public boolean MoveTorpedo6Times_Overcharging_Test(){
        initializeLeague(1);
        doSeriesOfEqualMoves("MOVE N TORPEDO", 6);

        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(new Point(7, 4), game.players[0].position) &&
                assertValue(new Point(7, 4), game.players[1].position) &&
                assertValue("0 -1 -1 -1", game.players[0].getCooldowns()) &&
                assertValue("0 -1 -1 -1", game.players[1].getCooldowns());
    }

    public boolean MoveTorpedo28Times_OverchargingSilence_Test(){
        initializePosition(2, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE", 28);

        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(new Point(14, 14), game.players[0].position) &&
                assertValue(new Point(14, 14), game.players[1].position) &&
                assertValue("3 4 0 -1", game.players[0].getCooldowns()) &&
                assertValue("3 4 0 -1", game.players[1].getCooldowns());
    }

    public boolean MoveTorpedo28Times_OverchargingSonar_Test(){
        initializePosition(2, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SONAR", 28);

        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(new Point(14, 14), game.players[0].position) &&
                assertValue(new Point(14, 14), game.players[1].position) &&
                assertValue("3 0 6 -1", game.players[0].getCooldowns()) &&
                assertValue("3 0 6 -1", game.players[1].getCooldowns());
    }

    public boolean MoveTorpedo28Times_OverchargingMine_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E MINE", 28);

        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(new Point(14, 14), game.players[0].position) &&
                assertValue(new Point(14, 14), game.players[1].position) &&
                assertValue("3 4 6 0", game.players[0].getCooldowns()) &&
                assertValue("3 4 6 0", game.players[1].getCooldowns());
    }

    public boolean PlaceMineAndTriggerOnSameTurn_Test(){
        initializePosition(3, new Point(0, 14));

        doSeriesOfEqualMoves("MOVE E MINE|MINE E|TRIGGER 4 14", 17);

        return assertValue(2, game.players[0].life) &&
                assertValue(3, game.players[1].life) &&
                assertValue(new Point(7, 14), game.players[0].mines.get(0).point) &&
                assertValue(new Point(6, 14), game.players[0].position) &&
                assertValue(new Point(6, 14), game.players[1].position) &&
                assertValue("3 4 6 3", game.players[0].getCooldowns()) &&
                assertValue("3 4 6 0", game.players[1].getCooldowns());
    }

    public boolean PlaceMineTriggerBeforeMove_Test(){
        initializePosition(3, new Point(0, 14));

        doSeriesOfEqualMoves("TRIGGER 4 14|MOVE E MINE|MINE E", 12);

        return assertValue(3, game.players[0].life) &&
                assertValue(4, game.players[1].life) &&
                assertValue(new Point(4, 14), game.players[0].position) &&
                assertValue(new Point(4, 14), game.players[1].position) &&
                assertValue("3 4 6 2", game.players[0].getCooldowns()) &&
                assertValue("3 4 6 2", game.players[1].getCooldowns());
    }


    public boolean Surface_LoseLife_Test(){
        initializeLeague(3);
        playerManager.setOutput(0, "MOVE N");
        game.onRound();
        playerManager.setOutput(1, "MOVE N");
        game.onRound();
        playerManager.setOutput(0, " SURFACE   |  MOVE S  ");
        game.onRound();
        game.onRound();

        return assertValue(5, p1.life) &&
                p1.getSummaries().equals("SURFACE 5|MOVE S") &&
                assertValue(new Point(7, 7), p1.position);
    }

    public boolean SilenceInvalidUsage_Test(){
        initializePosition(2, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE", 28);

        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(new Point(14, 14), game.players[0].position) &&
                assertValue(new Point(14, 14), game.players[1].position) &&
                assertValue("3 4 0 -1", game.players[0].getCooldowns()) &&
                assertValue("3 4 0 -1", game.players[1].getCooldowns());
    }

    public boolean MSG_Summaries_MultipleMessagesDoesntCrash_Test(){
        initializeLeague(3);
        playerManager.setOutput(0, "MSG yooo|SURFACE|MOVE N|MSG testing msg which can be bigass long.|MSG");
        game.onRound();
        if(!assertValue("yooo", p1.message)){
            return false;
        }
        game.onRound();
        if(!assertValue("testing msg which can be bigass long.", p1.message)){
            return false;
        }
        if(p1.getSummaries().contains("MSG")){
            return false;
        }

        playerManager.setOutput(1, "MOVE N");
        game.onRound();
        playerManager.setOutput(0, "MSG test1| SURFACE   | MOVE S  |MSG test2");
        game.onRound();
        if(!assertValue("test1", p1.message)){
            return false;
        }
        game.onRound();
        if(!assertValue("test2", p1.message)){
            return false;
        }

        return assertValue(4, p1.life) &&
                assertValue(new Point(7, 7), p1.position) &&
                assertValue("test2", p1.message) &&
                assertValue(false, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                p1.getSummaries().equals("SURFACE 5|MOVE S");
    }

    public boolean MSG_Invalid_Test(){
        initializeLeague(3);
        playerManager.setOutput(0, "SURFACE|MOVE N|MSGtesting msg which can be bigass long.");
        game.onRound();
        game.onRound();
        game.onRound();
        if(!assertValue("", p1.message)){
            return false;
        }

        return assertValue(-1, p1.life) &&
                assertValue(true, playerManager.disabled[0]);
    }

    public boolean Silence_InvalidUsage_TerminatesTheRightPlayer_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|SILENCE 69 2", 11);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue("3 4 0 3", game.players[0].getCooldowns()) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Silence_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|    SILENCE      69 2| SILENCE 69 2", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean Torpedo_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|    TORPEDO      69 2| TORPEDO    69 2", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean SONAR_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|SONAR 5|TORPEDO 5 5| SONAR 9", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean MINE_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|MINE N|TORPEDO 5 5| MINE S", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean MOVE_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|MOVE N|TORPEDO 5 5| MINE S", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean SURFACE_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("SURFACE 2 4|TRIGGER N|TORPEDO 5 5| SURFACE 5 52", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean TRIGGER_Duplicate_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("TRIGGER 2 4|TRIGGER N|TORPEDO 5 5| TRIGGER 5 52", 5);

        return assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean Silence_InvalidUsage_WithMine_TerminatesTheRightPlayer_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|MINE N|SILENCE 69 2", 11);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue("3 4 0 3", game.players[0].getCooldowns()) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Silence_CrossingAPath_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|SILENCE W 4", 12);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue("3 4 0 3", game.players[0].getCooldowns()) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Silence_SettingPath_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|SILENCE N 4", 12);
        if (!assertValue(true, p1.grid[6][13]) ||
                !assertValue(true, p1.grid[6][12]) ||
                !assertValue(true, p1.grid[6][11]) ||
                !assertValue(true, p1.grid[6][10])){
            return false;
        }
        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(false, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue(new Point(6, 10), p1.position) &&
                assertValue("3 4 6 3", game.players[0].getCooldowns()) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Silence_InSummaries_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|SILENCE N 4", 14);

        return assertValue("MOVE E|SILENCE", p1.getSummaries()) &&
                assertValue("MOVE E|SILENCE", p2.getSummaries());
    }


    public boolean Silence_MoveOntoIsland_Dead_Test(){
        initializePosition(3, new Point(10, 10));
        doSeriesOfEqualMoves("MOVE N SILENCE|SILENCE N 4", 12);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Silence_InvalidDistNegative_Dead_Test(){
        initializePosition(3, new Point(10, 10));
        doSeriesOfEqualMoves("MOVE N SILENCE|SILENCE N -1", 12);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Silence_InvalidDistToHigh_Dead_Test(){
        initializePosition(3, new Point(10, 10));
        doSeriesOfEqualMoves("MOVE N SILENCE|SILENCE N 5", 12);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean MoveOntoIsland_Dead_Test(){
        initializePosition(3, new Point(10, 10));
        doSeriesOfEqualMoves("MOVE N |||", 40);

        return assertValue(-1, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(true, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]);
    }

    public boolean Silence_0Distance_Test(){
        initializePosition(3, new Point(0, 14));
        doSeriesOfEqualMoves("MOVE E SILENCE|SILENCE N 0", 12);

        return assertValue(6, game.players[0].life) &&
                assertValue(6, game.players[1].life) &&
                assertValue(false, playerManager.disabled[0]) &&
                assertValue(false, playerManager.disabled[1]) &&
                assertValue(new Point(6, 14), p1.position) &&
                assertValue("3 4 6 3", game.players[0].getCooldowns()) &&
                assertValue("3 4 1 3", game.players[1].getCooldowns());
    }

    public boolean Sonar_WrongSector_Test(){
        initializePosition(3, new Point(0, 14));
        playerManager.setOutput(0, "MOVE E SONAR");
        playerManager.setOutput(1, "MOVE E SONAR");
        for(int i = 0; i < 4; i++){
            game.onRound();
            if(!assertValue("NA", p1.sonarResult)){
                return false;
            }
            game.onRound();
            if(!assertValue("NA", p2.sonarResult)){
                return false;
            }
        }
        playerManager.setOutput(0, "SONAR 1");
        game.onRound();
        if(!assertValue("N", p1.sonarResult)){
            return false;
        }

        game.onRound();
        game.onRound();
        if(!playerManager.inputs.get(playerManager.inputs.size()-1)[1].equals("N")){
            return false;
        }

        game.onRound();
        game.onRound();
        if(!assertValue("NA", p1.sonarResult)){
            return false;
        }
        playerManager.setOutput(1, "SONAR 10");
        game.onRound();

        return assertValue(true, playerManager.disabled[1])
                && assertValue(-1, p2.life);
    }


    public boolean Sonar_CorrectSector_Test(){
        initializePosition(3, new Point(0, 14));
        playerManager.setOutput(0, "MOVE E SONAR");
        playerManager.setOutput(1, "MOVE E SONAR");
        for(int i = 0; i < 4; i++){
            game.onRound();
            if(!assertValue("NA", p1.sonarResult)){
                return false;
            }
            game.onRound();
            if(!assertValue("NA", p2.sonarResult)){
                return false;
            }
        }
        playerManager.setOutput(0, "SONAR 7");
        game.onRound();
        if(!assertValue("Y", p1.sonarResult)){
            return false;
        }

        game.onRound();
        game.onRound();
        if(!playerManager.inputs.get(playerManager.inputs.size()-1)[1].equals("Y")){
            return false;
        }

        game.onRound();
        if(!assertValue("NA", p1.sonarResult)){
            return false;
        }
        playerManager.setOutput(1, "SONAR 10");
        game.onRound();
        game.onRound();

        return assertValue(true, playerManager.disabled[1])
                && assertValue(-1, p2.life);
    }

    public boolean longRound_CorrectMaxAndLastPlayer_Test(){
        initializePosition(3, new Point(0, 14));
        playerManager.setOutput(0, "SURFACE");
        playerManager.setOutput(1, "SURFACE");
        p1.life = 1000;
        p2.life = 1000;
        for(int i = 0; i < 800; i++){
            game.onRound();
        }

        return assertValue(1000-299, p1.life) &&
                assertValue(1000-299, p2.life);
    }

    public boolean fireAtIslands_Test(){
        initializePosition(3, new Point(8, 1));
        doSeriesOfEqualMoves("MOVE E TORPEDO|TORPEDO 10 0", 8);

        return assertValue(6, p1.life) &&
                assertValue(6, p2.life);
    }

    public boolean fireAtIslands_NoValid_Surface_Test(){
        initializePosition(3, new Point(8, 1));
        doSeriesOfEqualMoves("MOVE E TORPEDO", 6);
        playerManager.setOutput(0, "TORPEDO 10 0");
        game.onRound();

        return assertValue(5, p1.life) &&
                assertValue(6, p2.life);
    }

    public boolean fireAtIslands_p2MissIsland_NoValid_Surface_Test(){
        initializePosition(3, new Point(7, 1));
        doSeriesOfEqualMoves("MOVE E TORPEDO", 6);
        playerManager.setOutput(0, "TORPEDO 10 0");
        game.onRound();
        playerManager.setOutput(1, "TORPEDO 10 1");
        game.onRound();

        return assertValue(3, p1.life) &&
                assertValue(4, p2.life);
    }


    public boolean fireAOutsideRange_Test(){
        initializePosition(3, new Point(7, 1));
        doSeriesOfEqualMoves("MOVE E TORPEDO", 6);
        playerManager.setOutput(0, "TORPEDO 14 14");
        game.onRound();
        playerManager.setOutput(1, "TORPEDO 0 0");
        game.onRound();

        return assertValue(5, p1.life) &&
                assertValue(5, p2.life) &&
                assertValue("0 4 6 3", p1.getCooldowns()) &&
                assertValue("0 4 6 3", p2.getCooldowns());
    }

    public boolean fireJustInside_Test(){
        initializePosition(3, new Point(7, 1));
        doSeriesOfEqualMoves("MOVE E TORPEDO", 6);
        playerManager.setOutput(0, "TORPEDO 10 5");
        game.onRound();
        playerManager.setOutput(1, "TORPEDO 10 6");
        game.onRound();

        return assertValue(6, p1.life) &&
                assertValue(5, p2.life) &&
                assertValue("3 4 6 3", p1.getCooldowns()) &&
                assertValue("0 4 6 3", p2.getCooldowns());
    }

    private static < T > boolean assertValue(T expected, T actual){
        if((expected==null) != (actual==null))
            return throwError("Values not equal - \nexpected: "  +expected + "\nactual:   " + actual);
        if(!expected.equals(actual))
            return throwError("Values not equal - \nexpected: "  +expected + "\nactual:   " + actual);
        return true;
    }

    private static boolean assertDouble(double expected, double actual)
    {
        if(Math.abs(expected-actual) > 0.00001)
            return throwError("Values not equal: \nexpected: "  +expected + "\nactual:   " + actual);
        return true;
    }

    private static boolean throwError(String message){
        System.err.println("----------");
        System.err.println(message);
        System.err.println("----------");
        return false;
    }

    class TestPlayerManager implements IPlayerManager
    {
        public boolean gameOver = false;
        public boolean[] disabled = {false, false};
        public boolean[] executed = {false, false};
        public ArrayList<String[]> inputs = new ArrayList<>();
        public ArrayList<String> tooltips = new ArrayList<>();
        public ArrayList<String> summaries = new ArrayList<>();
        public int[] Scores = {0,0};
        private String[] outputs = {"", ""};
        public void setOutput(int playerId, String output){
            outputs[playerId] = output;
        }
        @Override
        public String getOutputs(int playerId) throws AbstractPlayer.TimeoutException {
            return outputs[playerId];
        }

        @Override
        public void sendData(String[] lines, int playerId) {
            inputs.add(lines);
        }

        @Override
        public void execute(int playerId) {
        }

        @Override
        public void updateScore(int playerId, int score) {
            Scores[playerId] = score;
        }

        @Override
        public void disablePlayer(int playerId, String reason) {
            disabled[playerId] = true;
        }

        @Override
        public void endGame() {
            gameOver = true;
        }

        @Override
        public void addTooltip(int playerId, String message) {
            tooltips.add(message);
        }

        @Override
        public void addGameSummary(int playerId, String message) {
            summaries.add(message);
        }
    }
}
