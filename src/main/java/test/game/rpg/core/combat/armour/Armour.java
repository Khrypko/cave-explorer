package test.game.rpg.core.combat.armour;

import java.io.Serializable;

/**
 * Created by Maks on 12.03.2019.
 */
public class Armour implements Serializable{

    private ArmourType type;
    private String name;
    private int defenceBonus;

    public Armour(ArmourType type, String name, int defenceBonus) {
        this.type = type;
        this.name = name;
        this.defenceBonus = defenceBonus;
    }

    public ArmourType getType() {
        return type;
    }

    public void setType(ArmourType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefenceBonus() {
        return defenceBonus;
    }

    public void setDefenceBonus(int defenceBonus) {
        this.defenceBonus = defenceBonus;
    }
}
