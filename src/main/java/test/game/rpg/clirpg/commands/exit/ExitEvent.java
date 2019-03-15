package test.game.rpg.clirpg.commands.exit;

import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.core.Game;
import test.game.rpg.core.messaging.Event;

/**
 * Created by Maks on 12.03.2019.
 */
public class ExitEvent implements Event {

    private Game game;
    private Terminal terminal;

    public ExitEvent(Game game, Terminal terminal) {
        this.game = game;
        this.terminal = terminal;
    }

    public void exit(){
        terminal.printLine("Please enter yes to confirm the exit, or anything else to continue");
        String answer = terminal.readLine();
        if ("yes".equals(answer)){
            game.stop();
        }
    }

}
