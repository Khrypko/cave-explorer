package test.game.rpg.core.map;

import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Event;

/**
 * Created by Maks on 10.03.2019.
 */
public class MoveEvent implements Event {

    private Vector coordinates;
    private GameObject object;

    public MoveEvent(Vector coordinates, GameObject object) {
        this.coordinates = coordinates;
        this.object = object;
    }

    public Vector getCoordinates() {
        return coordinates;
    }

    public GameObject getObject() {
        return object;
    }

}
