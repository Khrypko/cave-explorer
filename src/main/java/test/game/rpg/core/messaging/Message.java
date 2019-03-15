package test.game.rpg.core.messaging;

/**
 * Created by Maks on 10.03.2019.
 * Event that represent just some text information transfer
 */
public class Message implements Event {

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
