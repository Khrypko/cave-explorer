package test.game.rpg.clirpg.io;

/**
 * Created by Maks on 09.03.2019.
 * Factory to determine the Terminal implementation
 */
public class TerminalFactory {

    public static Terminal console(){
        if (System.console() == null){
            return new SystemOutTerminal();
        }

        return new ConsoleTerminal();
    }

}
