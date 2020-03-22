import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {
        
        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();
        // Set league, remove before push.
        gameRunner.setLeagueLevel(3);
        gameRunner.setSeed(1337L);
        // add player 1
        gameRunner.addAgent(DummyPlayer.class);
        // add player 2
        gameRunner.addAgent(DummyPlayer.class);
        //gameRunner.addAgent(Player2.class);

        //gameRunner.addAgent("python3 bot.py");
        //gameRunner.addAgent("python3 bot.py");
        //gameRunner.addAgent("java -cp target/test-classes/ Player"); //-agentlib:jdwp=transport=dt_socket,server=y,address=8681,suspend=y,quiet=y
        //gameRunner.addAgent("java -cp target/test-classes/ Player2"); //-agentlib:jdwp=transport=dt_socket,server=y,address=8682,suspend=y,quiet=y
        
        // gameRunner.addCommandLinePlayer("python src/main/resources/ai/ai.py");
        
        gameRunner.start();
    }
}
