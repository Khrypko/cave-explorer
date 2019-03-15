package test.game.rpg.clirpg.map;

import test.game.rpg.core.character.Experience;
import test.game.rpg.core.combat.CombatComponent;
import test.game.rpg.core.combat.Health;
import test.game.rpg.core.combat.Stats;
import test.game.rpg.core.combat.Treat;
import test.game.rpg.core.combat.armour.Armour;
import test.game.rpg.core.combat.armour.ArmourType;
import test.game.rpg.core.combat.weapon.Weapon;
import test.game.rpg.core.combat.weapon.WeaponType;
import test.game.rpg.core.npc.NpcComponent;
import test.game.rpg.core.npc.SimpleNpcAI;
import test.game.rpg.clirpg.renderer.GraphicsComponent;
import test.game.rpg.core.messaging.Message;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.*;
import test.game.rpg.core.messaging.Messenger;
import test.game.rpg.core.storage.MapData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Maks on 10.03.2019.
 */
//TODO: reimplement with autogeneration of maps
public class DummyGameWorld implements World {

    private GameMap currentMap;
    private GameObject player;
    private Vector startingPosition = Vector.with(1,1);
    private Messenger messenger;

    private DummyGameWorld(){}

    public DummyGameWorld(GameObject player, Messenger messenger) {
        this.messenger = messenger;

        this.player = player;
        this.player.addComponent(new TransformComponent(startingPosition));
        currentMap = generateTestMap();
    }

    private GameMap generateTestMap() {
        List<GameObject> gameObjects = generategameObj();
        CaveMap caveMap = new CaveMap("testMap", Vector.with(10, 10), this);
        caveMap.putGameObjects(gameObjects);
        return caveMap;
    }

    //TODO: remove
    private List<GameObject> generategameObj() {
        Random random = new Random();
        List<GameObject> gameObjects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i == 0) || (i == 9) || (j == 0) || (j == 9)){
                    gameObjects.add(createWall(i, j));
                }else if (random.nextInt(10 + 1) > 9){
                    gameObjects.add(createRandomEnemy(i, j));
                }
            }
        }
        gameObjects.add(player);

        return gameObjects;
    }

    private GameObject createWall(int x, int y) {
        GameObject gameObject = new GameObject("[wall-" + x + "-" + y + "]");
        String symbol = determineBorderSymbol(x, y);
        gameObject.addComponent(new TransformComponent(Vector.with(x, y)));
        if (x == 9 && y == 9){
            gameObject.addComponent(new GraphicsComponent(symbol, GraphicsComponent.ANSI_BLACK));
            gameObject.addComponent(new Exit());
        } else {
            gameObject.addComponent(new GraphicsComponent(symbol, GraphicsComponent.ANSI_BLUE));
        }

        return gameObject;
    }

    private GameObject createRandomEnemy(int x, int y) {
        GameObject gameObject = new GameObject("[object-" + x + "-" + y + "]");
        gameObject.addComponent(new GraphicsComponent("♟ ", GraphicsComponent.ANSI_RED));
        gameObject.addComponent(new TransformComponent(Vector.with(x, y)));
        gameObject.addComponent(new Experience(gameObject));
        gameObject.addComponent(new Health(10));
        gameObject.addComponent(new Stats(2, 2));
        gameObject.addComponent(new Treat(1, 1));
        gameObject.addComponent(getCombatComponent());
        gameObject.addComponent(new NpcComponent(new SimpleNpcAI(), gameObject));
        return gameObject;
    }

    private CombatComponent getCombatComponent() {
        CombatComponent combatComponent = new CombatComponent();
        combatComponent.setArmour(new Armour(ArmourType.NOT_ARMOR, "junk", 0));
        combatComponent.setWeapon(new Weapon(WeaponType.DAGGER, "dagger", Vector.with(1, 6), 3, Arrays.asList(18, 19, 20)));
        return combatComponent;
    }

    private String determineBorderSymbol(int i, int j) {
        if (i == 0 && j == 0){
            return "\u250C ";
        }
        if (i == 0 && j == 9){
            return  "\u2510 ";
        }
        if (i == 9 && j == 0){
            return "\u2514 ";
        }
        if (i == 9 && j == 9){
            return "\u2518 ";
        }
        if (i == 0 || i == 9){
            return "\u2500 ";
        }

        return "\u2502 ";
    }

    public GameMap getCurrentMap(){
        return currentMap;
    }

    public void changeMap(){
        this.player.addComponent(new TransformComponent(startingPosition));
        currentMap = generateTestMap();
        messenger.send(new Message("Huuhh... escaped another cave! I wonder, is it a way out there?.."));
    }

    public static DummyGameWorld loadWorld(GameObject player, MapData currentMapData){
        DummyGameWorld world = new DummyGameWorld();
        world.player = player;
        CaveMap currentMap = new CaveMap(currentMapData.getMapId(), currentMapData.getSize(), world);
        currentMap.putGameObjects(currentMapData.getGameObjects());
        world.currentMap = currentMap;
        return world;
    }
}
