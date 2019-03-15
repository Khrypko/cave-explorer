package test.game.rpg.clirpg.commands;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 * Factory that is used to parse user command and convert it to event
 */
public class CommandFactory {

    private Map<String, CommandParser> registeredCommands = new HashMap<>();

    public CommandFactory register(CommandParser commandParser){
        registeredCommands.put(commandParser.command(), commandParser);
        return this;
    }

    public Optional<Event> parseCommand(String commandString, GameObject objectToCommand, Game game){
        String[] command = commandString.split(" ");
        if (invalidCommand(command)) return Optional.empty();

        CommandParser parser = registeredCommands.get(command[0]);
        if (parser == null) return Optional.empty();

        return parser.parse(command, objectToCommand, game);
    }

    private boolean invalidCommand(String[] command) {
        return command.length == 0;
    }

}
