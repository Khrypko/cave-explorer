package test.game.rpg.clirpg.player.command.move;

import test.game.rpg.core.map.MoveEvent;
import test.game.rpg.core.map.TransformComponent;
import test.game.rpg.clirpg.commands.CommandParser;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.messaging.Event;

import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 */
public class MoveCommandParser implements CommandParser {
    @Override
    public String command() {
        return "move";
    }

    @Override
    public Optional<Event> parse(String[] commandData, GameObject object, Game game) {

        return extractDirection(commandData)
                .flatMap(direction -> calculateNewPosition(object, direction))
                .map(newPosition -> new MoveEvent(newPosition, object));
    }

    private Optional<String> extractDirection(String[] commandData) {
        try {
            return Optional.of(commandData[1]);
        } catch (IndexOutOfBoundsException e){
            return Optional.empty();
        }
    }

    private Optional<Vector> calculateNewPosition(GameObject objectToCommand, String direction) {
        return extractPosition(objectToCommand).flatMap(position -> adjustObjectPosition(direction, position.getPosition()));
    }

    private Optional<TransformComponent> extractPosition(GameObject objectToCommand) {
        return objectToCommand.getComponent(TransformComponent.class);
    }

    private Optional<Vector> adjustObjectPosition(String direction, Vector position){
        switch (direction){
            case "north":
                return Optional.of(Vector.with(position.getX() - 1, position.getY()));
            case "south":
                return Optional.of(Vector.with(position.getX() + 1, position.getY()));
            case "west":
                return Optional.of(Vector.with(position.getX(), position.getY() - 1));
            case "east":
                return Optional.of(Vector.with(position.getX(), position.getY() + 1));
            case "north-east":
                return Optional.of(Vector.with(position.getX() - 1, position.getY() + 1));
            case "north-west":
                return Optional.of(Vector.with(position.getX() - 1, position.getY() - 1));
            case "south-west":
                return Optional.of(Vector.with(position.getX() + 1, position.getY() - 1));
            case "south-east":
                return Optional.of(Vector.with(position.getX() + 1, position.getY() + 1));
            default:
                return Optional.empty();
        }
    }


}
