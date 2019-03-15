package test.game.rpg.core.combat.command;

import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.messaging.Event;

import java.util.List;

/**
 * Created by Maks on 12.03.2019.
 */
public class AttackEvent implements Event {

    private GameObject attacker;
    private List<Vector> attackCoord;

    public AttackEvent(GameObject attacker, List<Vector> attackCoord) {
        this.attacker = attacker;
        this.attackCoord = attackCoord;
    }

    public GameObject getAttacker() {
        return attacker;
    }

    public List<Vector> getAttackCoord() {
        return attackCoord;
    }
}
