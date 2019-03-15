package test.game.rpg.clirpg;

import org.junit.Before;
import org.junit.Test;
import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.core.CharacterBuilder;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.storage.GameStorage;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Maks on 15.03.2019.
 */
public class CLIGameFactoryTest {

    private CLIGameFactory gameFactory;
    private Terminal terminal;
    private GameStorage gameStorage;
    private CharacterBuilder characterBuilder;

    @Before
    public void setUp(){
        gameStorage = mock(GameStorage.class);
        terminal = mock(Terminal.class);
        when(terminal.readLine()).thenReturn("test");

        characterBuilder = mock(CharacterBuilder.class);
        when(characterBuilder.createCharacter("test")).thenReturn(new GameObject("test"));

        gameFactory = new CLIGameFactory(terminal, gameStorage, characterBuilder);

    }

    @Test
    public void testCliGameGactorySuccessfullyCreatesNewGame(){
        assertNotNull(gameFactory.createGame());
    }

}