package test.game.rpg.clirpg.commands.save;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;
import test.game.rpg.clirpg.commands.CommandParser;
import test.game.rpg.core.storage.SaveEvent;

import java.util.Optional;

/**
 * Created by Maks on 14.03.2019.
 */
public class SaveCommandParser implements CommandParser {
    @Override
    public String command() {
        return "save";
    }

    @Override
    public Optional<Event> parse(String[] commandData, GameObject objectToCommand, Game game) {
        if (commandData.length < 2){
            return Optional.empty();
        }
        return Optional.of(new SaveEvent(commandData[1]));
    }
}
