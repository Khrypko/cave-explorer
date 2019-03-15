package test.game.rpg.core.storage;

import test.game.rpg.core.Game;

import java.util.List;
import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 * Acts as a Repository abstraction
 */
public interface GameStorage {

    boolean save(String name, Game game);

    Optional<GameContainer> load(String name);

    List<String> listOfSavedFiles();
}
