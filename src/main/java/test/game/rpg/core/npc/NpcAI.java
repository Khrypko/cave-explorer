package test.game.rpg.core.npc;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Maks on 13.03.2019.
 * Should abstract NPC logic
 */
public interface NpcAI extends Serializable{

    Optional<Event> simulateTurn(GameObject object, Game game);

}
