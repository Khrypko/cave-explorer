package test.game.rpg.core.messaging;

/**
 * Created by Maks on 09.03.2019.
 * Used to receive notification about event that occurred, and handle it
 */
public interface Subscriber<T extends Event> {

    void notify(T event);

}
