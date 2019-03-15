package test.game.rpg.core.map;

/**
 * Created by Maks on 10.03.2019.
 * Represents entire game world
 */
public interface World {

    GameMap getCurrentMap();

    void changeMap();
}
