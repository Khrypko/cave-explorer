package test.game.rpg.core.map;

import test.game.rpg.core.GameObject;

import java.util.List;

/**
 * Created by Maks on 10.03.2019.
 * Represents game map
 */
public interface GameMap {

    String getId();

    List<GameObject> getAllObjects();

    GameObject getObjectOn(Vector coordinates);

    boolean isObjectOnMap(GameObject object);

    void addObjectToMap(GameObject gameObject);

    void removeObjectFromMap(GameObject gameObject);

    void moveObject(GameObject object, Vector newPosition);

    Vector size();

}
