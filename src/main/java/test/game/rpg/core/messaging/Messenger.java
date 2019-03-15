package test.game.rpg.core.messaging;

import java.util.*;

/**
 * Created by Maks on 09.03.2019.
 * Represents "messaging service" that keeps subscribers linked to specific events
 * Once some event is published via send(Event event) method, it should be delivered to all interested in it subscribers
 */
public class Messenger {

    private Map<Class<? extends Event>, Subscribers> subscriptions;

    public Messenger() {
        this.subscriptions = new HashMap<>();
    }

    public void send(Event event){
        Subscribers subscribers = subscriptions.get(event.getClass());
        if (subscribers != null){
            subscribers.getSubscribers().forEach(subscriber -> subscriber.notify(event));
        }
    }

    public <T extends Event> void subscribe(Class<T> messageType, Subscriber<T> subscriber) {
        this.subscriptions.compute(
                messageType, (type, subscribers) -> subscribers == null ? Subscribers.instance(subscriber) : subscribers.addSubscriber(subscriber)
        );
    }

    public void unsubscribe(Class<? extends Event> messageType, Subscriber subscriber) {
        Subscribers subscribers = subscriptions.get(messageType);
        if (subscribers != null){
            subscribers.removeSubscriber(subscriber);
        }
    }

    private static class Subscribers {
        private List<Subscriber> subscribers = new ArrayList<>();

        public Subscribers(Subscriber subscriber) {
            this.subscribers.add(subscriber);
        }

        public List<Subscriber> getSubscribers() {
            return subscribers;
        }

        public Subscribers addSubscriber(Subscriber subscriber){
            if (!this.subscribers.contains(subscriber)){
                this.subscribers.add(subscriber);
            }
            return this;
        }

        public Subscribers removeSubscriber(Subscriber subscriber){
            this.subscribers.remove(subscriber);
            return this;
        }

        public static Subscribers instance(Subscriber subscriber){
            return new Subscribers(subscriber);
        }
    }

}
