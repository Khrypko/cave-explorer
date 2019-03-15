package test.game.rpg.core.combat;

import org.junit.Before;
import org.junit.Test;
import test.game.rpg.clirpg.map.CaveMap;
import test.game.rpg.clirpg.renderer.GraphicsComponent;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.character.Experience;
import test.game.rpg.core.combat.armour.Armour;
import test.game.rpg.core.combat.armour.ArmourType;
import test.game.rpg.core.combat.command.AttackEvent;
import test.game.rpg.core.combat.weapon.Weapon;
import test.game.rpg.core.combat.weapon.WeaponType;
import test.game.rpg.core.map.CaveMapTest;
import test.game.rpg.core.map.TransformComponent;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.map.World;
import test.game.rpg.core.messaging.Messenger;
import test.game.rpg.core.npc.NpcComponent;
import test.game.rpg.core.npc.SimpleNpcAI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Maks on 15.03.2019.
 */
public class CombatSystemTest {

    private CombatSystem combatSystem;
    private GameObject player;
    private GameObject enemy;
    private Game game;
    private Messenger messenger;

    @Before
    public void setUp(){
        this.game = mock(Game.class);

        this.messenger = new Messenger();
        this.messenger = spy(messenger);
        when(this.game.getMessenger()).thenReturn(messenger);

        CaveMap map = new CaveMap("test", Vector.with(10, 10), null);
        List<GameObject> gameObjects = initializeGameObjects();
        this.player = gameObjects.get(0);
        this.enemy = gameObjects.get(1);
        map.putGameObjects(gameObjects);
        World world = mock(World.class);
        when(world.getCurrentMap()).thenReturn(map);

        when(game.getWorld()).thenReturn(world);

        this.combatSystem = new CombatSystem();
        combatSystem.initialize(game);
    }

    @Test
    public void testAttackCommandIsExecutedDuringTurnSimulation(){
        Health enemyHealth = enemy.getComponent(Health.class).get();

        int beforeHealthAmount = enemyHealth.getCurrentHealth();

        messenger.send(new AttackEvent(player, Collections.singletonList(Vector.with(1, 2))));

        combatSystem.run();
        int afterAttackHealthAmount = enemyHealth.getCurrentHealth();

        assertTrue(afterAttackHealthAmount < beforeHealthAmount);
    }

    private List<GameObject> initializeGameObjects() {
        GameObject player = new GameObject("player");
        player.addComponent(new TransformComponent(Vector.with(1, 1)));
        player.addComponent(new Experience(player));
        player.addComponent(new Health(10));
        player.addComponent(new Stats(20, 2));
        player.addComponent(new Treat(10, 1));
        player.addComponent(getCombatComponent());

        GameObject enemy = new GameObject("enemy");
        enemy.addComponent(new TransformComponent(Vector.with(1, 2)));
        enemy.addComponent(new Experience(enemy));
        enemy.addComponent(new Health(10));
        enemy.addComponent(new Stats(2, 2));
        enemy.addComponent(new Treat(1, 1));
        enemy.addComponent(getCombatComponent());
        enemy.addComponent(new NpcComponent(new SimpleNpcAI(), enemy));

        return Arrays.asList(player, enemy);
    }

    private CombatComponent getCombatComponent() {
        CombatComponent combatComponent = new CombatComponent();
        combatComponent.setArmour(new Armour(ArmourType.NOT_ARMOR, "junk", 0));
        combatComponent.setWeapon(new Weapon(WeaponType.DAGGER, "dagger", Vector.with(1, 6), 3, Arrays.asList(18, 19, 20)));
        return combatComponent;
    }
}