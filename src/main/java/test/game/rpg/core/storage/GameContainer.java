package test.game.rpg.core.storage;

import test.game.rpg.core.GameObject;

/**
 * Created by Maks on 14.03.2019.
 * Class that should be used to persist game data
 */
public interface GameContainer {

    String getName();

    GameObject getPlayer();

    MapData getMapData();
}
