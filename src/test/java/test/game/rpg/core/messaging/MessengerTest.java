package test.game.rpg.core.messaging;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Maks on 10.03.2019.
 */
public class MessengerTest {

    private Messenger messenger;

    @Before
    public void setUp(){
        messenger = new Messenger();
    }

    @Test
    public void testAddingSubscriberAndSendingMessageSuccessful(){

        Subscriber subscriber = mock(Subscriber.class);

        messenger.subscribe(TestEvent.class, subscriber);
        Event event = new TestEvent();

        messenger.send(event);

        verify(subscriber).notify(event);
    }

    @Test
    public void testAddSeveralSubscribersForOneMessageTypeAndSendMessageSuccessful(){
        Subscriber first = mock(Subscriber.class);
        Subscriber second = mock(Subscriber.class);

        messenger.subscribe(TestEvent.class, first);
        messenger.subscribe(TestEvent.class, second);

        Event event = new TestEvent();

        messenger.send(event);

        verify(first).notify(event);
        verify(second).notify(event);

    }

    @Test
    public void testAddSubscribersForDifferentMessagesTypesAndSendMessageTriggeredOnlyOneOfThem(){
        Subscriber first = mock(Subscriber.class);
        Subscriber second = mock(Subscriber.class);

        messenger.subscribe(TestEvent.class, first);
        messenger.subscribe(OtherTestEvent.class, second);

        Event event = new TestEvent();

        messenger.send(event);

        verify(first).notify(event);
        verify(second, Mockito.never()).notify(event);
    }

    @Test
    public void testSubscriberIsRemovedAndWontReceiveMessage(){
        Subscriber first = mock(Subscriber.class);
        Subscriber second = mock(Subscriber.class);

        messenger.subscribe(TestEvent.class, first);
        messenger.subscribe(TestEvent.class, second);

        messenger.unsubscribe(TestEvent.class, second);

        Event event = new TestEvent();

        messenger.send(event);

        verify(first).notify(event);
        verify(second, Mockito.never()).notify(event);
    }

    private static class TestEvent implements Event {

    }

    private static class OtherTestEvent implements Event {

    }

}