package test.game.rpg.core.character;

import org.junit.Before;
import org.junit.Test;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.combat.command.DeathEvent;
import test.game.rpg.core.messaging.Messenger;

import static org.junit.Assert.*;

/**
 * Created by Maks on 15.03.2019.
 */
public class ExperienceTest {

    private Experience experience;
    private Messenger messenger;
    private GameObject gameObject;
    private GameObject enemy;

    @Before
    public void setUp(){
        enemy = new GameObject("enemy");
        enemy.addComponent(new Experience(enemy));

        gameObject = new GameObject("test");
        experience = new Experience(gameObject);
        messenger = new Messenger();

        messenger.subscribe(DeathEvent.class, experience);
    }

    @Test
    public void testExperienceIsRecalculatedAfterEnemyDies(){
        int initExp = experience.getAmount();

        messenger.send(new DeathEvent(enemy));

        int newAmount = experience.getAmount();
        assertTrue(initExp < newAmount);
    }
}