package test.game.rpg.core.storage;

import test.game.rpg.core.messaging.Event;

/**
 * Created by Maks on 14.03.2019.
 * Event that should be issued when user requested save
 */
public class SaveEvent implements Event {

    private String saveName;

    public SaveEvent(String saveName) {
        this.saveName = saveName;
    }

    public String getSaveName() {
        return saveName;
    }
}
