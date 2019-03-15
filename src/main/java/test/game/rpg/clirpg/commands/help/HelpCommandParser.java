package test.game.rpg.clirpg.commands.help;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;
import test.game.rpg.clirpg.commands.CommandParser;

import java.util.Optional;

/**
 * Created by Maks on 14.03.2019.
 */
public class HelpCommandParser implements CommandParser {
    @Override
    public String command() {
        return "help";
    }

    @Override
    public Optional<Event> parse(String[] commandData, GameObject objectToCommand, Game game) {
        return Optional.of(new HelpEvent());
    }
}
