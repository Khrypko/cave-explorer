package test.game.rpg.clirpg.commands.exit;

import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.clirpg.io.TerminalFactory;
import test.game.rpg.clirpg.commands.CommandParser;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;

import java.util.Optional;

/**
 * Created by Maks on 12.03.2019.
 */
public class ExitCommandParser implements CommandParser {

    Terminal terminal = TerminalFactory.console();

    @Override
    public String command() {
        return "exit";
    }

    @Override
    public Optional<Event> parse(String[] commandData, GameObject objectToCommand, Game game) {
        game.getMessenger().subscribe(ExitEvent.class, new ExitCommandSubscriber());
        return Optional.of(new ExitEvent(game, terminal));
    }
}
