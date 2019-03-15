package test.game.rpg.core.combat.weapon;

import org.junit.Before;
import org.junit.Test;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.TransformComponent;
import test.game.rpg.core.map.Vector;

import static org.junit.Assert.*;
import static test.game.rpg.core.combat.weapon.WeaponType.AXE;
import static test.game.rpg.core.combat.weapon.WeaponType.SPEAR;
import static test.game.rpg.core.combat.weapon.WeaponType.SWORD;

/**
 * Created by Maks on 15.03.2019.
 */
public class WeaponTypeTest {

    private GameObject object;

    @Before
    public void setUp(){
        object = new GameObject("test");
        object.addComponent(new TransformComponent(Vector.with(1, 1)));
    }

    @Test
    public void testThatTargetShouldBeInReachOfSword(){
        assertTrue(SWORD.isAttackReachesTarget(object, getCloseEnemy(), null));
    }

    @Test
    public void testThatTargetShouldNotBeInReachOfSword(){
        assertFalse(SWORD.isAttackReachesTarget(object, getFarAwayEnemy(), null));
    }

    @Test
    public void testThatTargetShouldBeInReachOfAxe(){
        assertTrue(AXE.isAttackReachesTarget(object, getCloseEnemy(), null));
    }

    @Test
    public void testThatTargetShouldNotBeInReachOfAxe(){
        assertFalse(AXE.isAttackReachesTarget(object, getFarAwayEnemy(), null));
    }

    @Test
    public void testThatTargetShouldBeInReachOfSpear(){
        assertTrue(SPEAR.isAttackReachesTarget(object, getCloseEnemy(), null));
    }

    @Test
    public void testThat2CellsAwayTargetShouldBeInReachOfSpear(){
        assertTrue(SPEAR.isAttackReachesTarget(object, get2CellsAwayEnemy(), null));
    }

    @Test
    public void testThatTargetShouldNotBeInReachOfSpear(){
        assertFalse(SPEAR.isAttackReachesTarget(object, getFarAwayEnemy(), null));
    }



    private GameObject getCloseEnemy() {
        GameObject gameObject = new GameObject("close enemy");
        gameObject.addComponent(new TransformComponent(Vector.with(1, 2)));
        return gameObject;
    }

    private GameObject get2CellsAwayEnemy() {
        GameObject gameObject = new GameObject("2 cellc away enemy");
        gameObject.addComponent(new TransformComponent(Vector.with(1, 3)));
        return gameObject;
    }

    private GameObject getFarAwayEnemy() {
        GameObject gameObject = new GameObject("Far away enemy");
        gameObject.addComponent(new TransformComponent(Vector.with(9, 9)));
        return gameObject;
    }
}