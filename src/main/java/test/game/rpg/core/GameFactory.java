package test.game.rpg.core;

import java.util.List;
import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 * Should be used to get instance of a game. Responsible for all configurations on Game
 */
public interface GameFactory {

    Game createGame();

    Optional<Game> loadGame(String gameId);

    List<String> listOfSavedGames();

}
