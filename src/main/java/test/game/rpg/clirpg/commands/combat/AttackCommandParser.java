package test.game.rpg.clirpg.commands.combat;

import test.game.rpg.core.combat.command.AttackEvent;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.messaging.Event;
import test.game.rpg.clirpg.commands.CommandParser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Maks on 12.03.2019.
 */
public class AttackCommandParser implements CommandParser {
    @Override
    public String command() {
        return "attack";
    }

    @Override
    public Optional<Event> parse(String[] commandData, GameObject objectToCommand, Game game) {
        List<Vector> attackCoordinates = extractAttackCoord(commandData);
        if (attackCoordinates == null) return Optional.empty();
       return Optional.of(new AttackEvent(objectToCommand, attackCoordinates));
    }

    /**
     * Implemented only for single target attack
     * @return attack targets coordinates
     */
    private List<Vector> extractAttackCoord(String[] commandData) {
        if (commandData.length < 2) return null;
        String stringCoords = commandData[1];
        Vector coords = parseCoordinateString(stringCoords);
        if (coords == null) return null;

        return Collections.singletonList(coords);
    }

    private Vector parseCoordinateString(String stringCoords) {
        try {
            String[] coordsArray = stringCoords.split(":");
            if (coordsArray.length < 2) return null;
            int x = Integer.parseInt(coordsArray[0]);
            int y = Integer.parseInt(coordsArray[1]);
            return Vector.with(x, y);
        } catch (NumberFormatException e ){
            return null;
        }
    }
}
