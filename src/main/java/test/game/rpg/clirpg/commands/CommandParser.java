package test.game.rpg.clirpg.commands;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;

import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 * Responsible for parsing already known user command type and convert it to proper event
 *
 * command() method should return string command, that can be parsed by the specific implementation
 */
public interface CommandParser {

    String command();

    Optional<Event> parse(String[] commandData, GameObject objectToCommand, Game game);

}
