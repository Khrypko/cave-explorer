package test.game.rpg.core.combat;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 12.03.2019.
 */
public class Treat implements Component {

    private int baseAttackModifier;
    private int defenceClass;

    public Treat(int baseAttackModifier, int defenceClass) {
        this.baseAttackModifier = baseAttackModifier;
        this.defenceClass = defenceClass;
    }

    public int getBaseAttackModifier() {
        return baseAttackModifier;
    }

    public void setBaseAttackModifier(int baseAttackModifier) {
        this.baseAttackModifier = baseAttackModifier;
    }

    public int getDefenceClass() {
        return defenceClass;
    }

    public void setDefenceClass(int defenceClass) {
        this.defenceClass = defenceClass;
    }
}
