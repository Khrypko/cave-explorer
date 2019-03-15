package test.game.rpg.core.storage;

import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.Vector;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Maks on 14.03.2019.
 * Saves data about current map
 */
public class MapData implements Serializable{

    private String mapId;
    private List<GameObject> gameObjects;
    private Vector size;

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }
}
