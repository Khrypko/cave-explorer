package test.game.rpg.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import test.game.rpg.core.component.Component;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Maks on 10.03.2019.
 */
public class GameObjectTest {

    private GameObject gameObject;

    @Before
    public void setUp(){
        this.gameObject = new GameObject("test");
    }

    @Test
    public void testComponentAddedAndCanBeRetrieved(){
        Component component = new TestComponent();

        gameObject.addComponent(component);

        Optional<TestComponent> retrieved = gameObject.getComponent(TestComponent.class);

        assertTrue(retrieved.isPresent());
        assertEquals(component, retrieved.get());
    }

    @Test
    public void testEmptyOptionalReturnedIfNoComponentFound(){
        Optional<TestComponent> retrieved = gameObject.getComponent(TestComponent.class);
        assertFalse(retrieved.isPresent());
    }

    private static class TestComponent implements Component{

    }

}