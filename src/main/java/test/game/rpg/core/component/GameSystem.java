package test.game.rpg.core.component;

import test.game.rpg.core.Game;

/**
 * Created by Maks on 09.03.2019.
 * One of the game systems. Like: rendered, user input resolver, npc logic processor and so on
 */
public interface GameSystem {

    void run();

    void initialize(Game game);
}
