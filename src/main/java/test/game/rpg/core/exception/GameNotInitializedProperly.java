package test.game.rpg.core.exception;

/**
 * Created by Maks on 10.03.2019.
 */
public class GameNotInitializedProperly extends RuntimeException {

    public GameNotInitializedProperly(String message) {
        super(message);
    }

    public GameNotInitializedProperly(String message, Throwable cause) {
        super(message, cause);
    }
}
