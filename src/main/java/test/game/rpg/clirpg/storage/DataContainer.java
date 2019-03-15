package test.game.rpg.clirpg.storage;

import test.game.rpg.core.GameObject;
import test.game.rpg.core.storage.GameContainer;
import test.game.rpg.core.storage.MapData;

import java.io.Serializable;

/**
 * Created by Maks on 14.03.2019.
 */
public class DataContainer implements GameContainer, Serializable {

    private static final long serialVersionUID = 1711121676280436430L;

    private String name;
    private GameObject player;
    private MapData mapData;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameObject getPlayer() {
        return player;
    }

    public void setPlayer(GameObject player) {
        this.player = player;
    }

    public MapData getMapData() {
        return mapData;
    }

    public void setMapData(MapData mapData) {
        this.mapData = mapData;
    }
}
