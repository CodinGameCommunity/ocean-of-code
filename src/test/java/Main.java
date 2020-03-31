import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {

    public static void main(String[] args) {
        // read cli args
        Options opts = new Options().read(args);
        String cli1 = opts.getCommandLine1();
        String cli2 = opts.getCommandLine2();

        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();
        // Set league, remove before push.
        gameRunner.setLeagueLevel(3);
        gameRunner.setSeed(1337L);
        // add player 1
        if (null != cli1) {
            gameRunner.addAgent(cli1);
        } else {
            gameRunner.addAgent(DummyPlayer.class);
        }
        // add player 2
        if (null != cli2) {
            gameRunner.addAgent(cli2);
        } else {
            gameRunner.addAgent(DummyPlayer.class);
        }
        //gameRunner.addAgent(Player2.class);

        //gameRunner.addAgent("python3 bot.py");
        //gameRunner.addAgent("python3 bot.py");
        //gameRunner.addAgent("java -cp target/test-classes/ Player"); //-agentlib:jdwp=transport=dt_socket,server=y,address=8681,suspend=y,quiet=y
        //gameRunner.addAgent("java -cp target/test-classes/ Player2"); //-agentlib:jdwp=transport=dt_socket,server=y,address=8682,suspend=y,quiet=y

        // gameRunner.addCommandLinePlayer("python src/main/resources/ai/ai.py");

        gameRunner.start();
    }
}

class Options {
    private String commandLine1 = null;
    private String commandLine2 = null;

    public Options read(String[] args) {
        if (args == null) {
            return this;
        }

        switch (args.length) {
            case 1: {
                this.commandLine1 = args[0];
                break;
            }
            case 2: {
                this.commandLine1 = args[0];
                this.commandLine2 = args[1];
                break;
            }
        }

        return this;
    }

    public String getCommandLine1() {
        return this.commandLine1;
    }

    public String getCommandLine2() {
        return this.commandLine2;
    }
}
