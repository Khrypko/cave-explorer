package test.game.rpg.core.map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import test.game.rpg.clirpg.map.CaveMap;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.messaging.Messenger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Maks on 15.03.2019.
 */
public class TransformSystemTest {

    private TransformSystem transformSystem;
    private Game game;
    private Messenger messenger;

    @Before
    public void setUp(){
        this.game = mock(Game.class);

        this.messenger = new Messenger();
        this.messenger = spy(messenger);
        when(this.game.getMessenger()).thenReturn(messenger);

        this.transformSystem = new TransformSystem();
        this.transformSystem.initialize(game);
    }

    @Test
    public void testMoveCommandProcessedCorrectly(){
        CaveMap map = new CaveMap("test", Vector.with(10, 10), null );
        GameObject object = setUpObject();
        map.addObjectToMap(object);
        World world = mock(World.class);
        when(world.getCurrentMap()).thenReturn(map);

        when(game.getWorld()).thenReturn(world);

        game.getMessenger().send(new MoveEvent(Vector.with(1, 2), object));

        transformSystem.run();

        Vector newPosition = object.getComponent(TransformComponent.class).get().getPosition();
        assertEquals(Vector.with(1, 2), newPosition);
    }

    private GameObject setUpObject() {
        GameObject object = new GameObject("test");
        object.addComponent(new TransformComponent(Vector.with(1, 1)));
        return object;
    }

}