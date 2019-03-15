package test.game.rpg.core.character;

import test.game.rpg.core.combat.command.DeathEvent;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.component.Component;
import test.game.rpg.core.messaging.Subscriber;

import java.util.Optional;

/**
 * Created by Maks on 12.03.2019.
 */
public class Experience implements Component, Subscriber<DeathEvent> {

    private static final int BASE_EXP_REWARD_AMOUNT = 20;

    private int amount = 0;

    private GameObject object;

    public Experience(GameObject object) {
        this.object = object;
    }

    @Override
    public void notify(DeathEvent command) {
        GameObject dead = command.getDeadObject();
        if (!this.object.equals(dead)){
            Optional<Experience> deadExperience = dead.getComponent(Experience.class);
            deadExperience.ifPresent(exp -> this.amount += exp.getLevel() * BASE_EXP_REWARD_AMOUNT);
        }
    }

    public int getAmount() {
        return amount;
    }

    public int getLevel(){
        if (this.amount == 0) return 1;
        return this.amount / 100 + 1;
    }
}
