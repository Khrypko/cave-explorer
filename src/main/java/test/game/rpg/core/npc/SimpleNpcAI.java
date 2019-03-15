package test.game.rpg.core.npc;

import test.game.rpg.core.combat.command.AttackEvent;
import test.game.rpg.core.map.MoveEvent;
import test.game.rpg.core.map.TransformComponent;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.messaging.Event;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by Maks on 13.03.2019.
 *
 * Npc, that is using this AI will simply wait wor player to close down the distance with him.
 * After hero will come closer than 3 cells, unit will start to move towards him, and in case if player wil be next to him - attack
 */
public class SimpleNpcAI implements NpcAI {

    private static final int MIN_DISTANCE = 3;

    @Override
    public Optional<Event> simulateTurn(GameObject object, Game game) {
        Optional<Vector> npcPosition = objectPosition(object);
        Optional<Vector> playerPosition = objectPosition(game.getPlayer());

        if (!npcPosition.isPresent() || !playerPosition.isPresent()){
            return Optional.empty();
        }
        int distanceToPlayer = calculateDistanceToPlayer(npcPosition.get(), playerPosition.get());

        if (distanceToPlayer <= MIN_DISTANCE){
            if (distanceToPlayer == 1){
                return Optional.of(attack(object, playerPosition.get()));
            } else {
                return Optional.of(moveTowardsPlayer(object, npcPosition.get(), playerPosition.get()));
            }
        }

        return Optional.empty();
    }

    private Event attack(GameObject object, Vector playerPosition) {
        return new AttackEvent(object, Collections.singletonList(playerPosition));
    }

    private Event moveTowardsPlayer(GameObject object, Vector npcPosition, Vector playerPosition) {
        Vector newCoords = calculateNewPosition(npcPosition, playerPosition);
        return new MoveEvent(newCoords, object);
    }

    private Vector calculateNewPosition(Vector npcPosition, Vector playerPosition) {
        int x = npcPosition.getX();
        int y = npcPosition.getY();

        if (npcPosition.getX() < playerPosition.getX()){
            x = npcPosition.getX() + 1;
        } else if (npcPosition.getX() > playerPosition.getX()){
            x = npcPosition.getX() - 1;
        }

        if (npcPosition.getY() < playerPosition.getY()){
            y = npcPosition.getY() + 1;
        } else if (npcPosition.getY() > playerPosition.getY()){
            y = npcPosition.getY() - 1;
        }

        return Vector.with(x, y);
    }

    private int calculateDistanceToPlayer(Vector npcPosition, Vector playerPosition) {

        int deltaX = Math.abs(npcPosition.getX() - playerPosition.getX());
        int deltaY = Math.abs(npcPosition.getY() - playerPosition.getY());

        int min = Math.min(deltaX, deltaY);
        int max = Math.max(deltaX, deltaY);

        int diagonalSteps = min;
        int straitSteps = max - min;

        return diagonalSteps + straitSteps;
    }

    private Optional<Vector> objectPosition(GameObject object){
        return object.getComponent(TransformComponent.class).map(TransformComponent::getPosition);
    }
}
