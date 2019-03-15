package test.game.rpg.core;

import org.junit.Before;
import org.junit.Test;
import test.game.rpg.core.combat.command.DeathEvent;
import test.game.rpg.core.engine.GameEngine;
import test.game.rpg.core.exception.GameNotInitializedProperly;
import test.game.rpg.core.map.World;
import test.game.rpg.core.messaging.Messenger;
import test.game.rpg.core.storage.GameStorage;
import test.game.rpg.core.storage.SaveEvent;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Maks on 10.03.2019.
 */
public class GameTest {

    private Game game;
    private GameStorage storage;
    private GameEngine engine;
    private World world;
    private Messenger messenger;
    private GameObject player;

    @Before
    public void setUp(){
        this.storage = mock(GameStorage.class);
        this.engine = mock(GameEngine.class);
        this.world = mock(World.class);
        this.messenger = new Messenger();
        this.messenger = spy(Messenger.class);
        this.player = createPlayer();
        this.game = Game.builder().storage(storage).engine(engine).name("test").messenger(messenger).world(world).player(player).build();
    }

    private GameObject createPlayer() {
        GameObject player = new GameObject("player");
        return player;
    }

    @Test
    public void testGameRunningFlagSetToTrueAfterStart(){
        game.start();
        assertTrue(game.isRunning());
    }

    @Test
    public void testGameIsRunningFlagSetToFalseAfterGameStopped(){
        game.start();
        assertTrue(game.isRunning());

        game.stop();

        assertFalse(game.isRunning());
    }

    @Test
    public void testSaveMethodTriggersStorageSave(){
        doCallRealMethod().when(messenger).send(any());
        doCallRealMethod().when(messenger).subscribe(any(), any());

        when(storage.save(any(), any())).thenReturn(true);

        game.start();

        messenger.send(new SaveEvent("test"));

        verify(storage).save("test", game);
    }

    @Test
    public void testOnGameObjectDeathMethodEndsGameIfPlayerDied(){
        doCallRealMethod().when(messenger).send(any());
        doCallRealMethod().when(messenger).subscribe(any(), any());

        game.start();

        messenger.send(new DeathEvent(player));

        assertFalse(game.isRunning());
    }

    @Test
    public void testGameEngineIsInitializedOnStart(){
        game.start();
        verify(engine).initialize(game);
    }

    @Test(expected = GameNotInitializedProperly.class)
    public void testBuilderFailsWithoutEngine(){
        Game.builder().name("test").storage(storage).player(player).world(world).build();
    }

    @Test(expected = GameNotInitializedProperly.class)
    public void testBuilderFailsWithoutStorage(){
        Game.builder().name("test").engine(engine).player(player).world(world).build();
    }

    @Test(expected = GameNotInitializedProperly.class)
    public void testBuilderFailsWithoutName(){
        Game.builder().engine(engine).storage(storage).player(player).world(world).build();
    }

    @Test(expected = GameNotInitializedProperly.class)
    public void testBuilderFailsWithoutWorld(){
        Game.builder().engine(engine).storage(storage).player(player).name("test").build();
    }

    @Test(expected = GameNotInitializedProperly.class)
    public void testBuilderFailsWithoutMessenger(){
        Game.builder().engine(engine).storage(storage).name("test").player(player).build();
    }

    @Test(expected = GameNotInitializedProperly.class)
    public void testBuilderFailsWithoutPlayer(){
        Game.builder().engine(engine).storage(storage).name("test").messenger(messenger).build();
    }


}