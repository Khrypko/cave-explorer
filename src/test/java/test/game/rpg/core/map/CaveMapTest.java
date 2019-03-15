package test.game.rpg.core.map;

import org.junit.Before;
import org.junit.Test;
import test.game.rpg.clirpg.map.CaveMap;
import test.game.rpg.core.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Maks on 10.03.2019.
 */
public class CaveMapTest {

    private CaveMap caveMap;

    @Before
    public void setUp(){
        caveMap = new CaveMap("test_map", Vector.with(10, 10), null);
        caveMap.putGameObjects(generateTestMap());
    }

    @Test
    public void testAlObjectsPutToMap(){
        List<GameObject> map = generateTestMap();

        this.caveMap.putGameObjects(map);

        List<GameObject> gameObjects = this.caveMap.getAllObjects();

        assertEquals(map.size(), gameObjects.size());
    }

    @Test
    public void testGetObjectsByCoordinateReturnsProperObject(){
        String id = "[wall-0-0]";

        GameObject wall = caveMap.getObjectOn(new Vector(0,0));

        assertNotNull(wall);
        assertEquals(id, wall.getId());
    }

    private List<GameObject> generateTestMap() {
        Random random = new Random();
        List<GameObject> gameObjects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9){
                    gameObjects.add(createObject("[wall-" + i + "-" + j + "]", i, j));
                } else {
                    if (random.nextInt(10 + 1) > 5){
                        gameObjects.add(createObject("[object-" + i + "-" + j + "]", i, j));
                    }
                }
            }
        }
        return gameObjects;
    }

    private GameObject createObject(String id, int x, int y) {
        GameObject gameObject = new GameObject(id);
        gameObject.addComponent(new TransformComponent(Vector.with(x, y)));
        return gameObject;
    }
}