package test.game.rpg.core.combat.command;

import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;

/**
 * Created by Maks on 12.03.2019.
 */
public class DeathEvent implements Event {

    private GameObject deadObject;

    public DeathEvent(GameObject deadObject) {
        this.deadObject = deadObject;
    }

    public GameObject getDeadObject() {
        return deadObject;
    }
}
