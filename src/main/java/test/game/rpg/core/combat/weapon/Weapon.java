package test.game.rpg.core.combat.weapon;

import test.game.rpg.core.map.Vector;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Maks on 12.03.2019.
 */
public class Weapon implements Serializable{

    private WeaponType type;
    private String name;
    private Vector demageBone;
    private int critModifier;
    private List<Integer> critOn;

    public Weapon(WeaponType type, String name, Vector demageBone, int critModifier, List<Integer> critOn) {
        this.type = type;
        this.name = name;
        this.demageBone = demageBone;
        this.critModifier = critModifier;
        this.critOn = critOn;
    }

    public WeaponType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Vector getDemageBone() {
        return demageBone;
    }

    public int getCritModifier() {
        return critModifier;
    }

    public List<Integer> getCritOn() {
        return critOn;
    }
}
