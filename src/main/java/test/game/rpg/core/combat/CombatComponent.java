package test.game.rpg.core.combat;

import test.game.rpg.core.combat.armour.Armour;
import test.game.rpg.core.combat.weapon.Weapon;
import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 12.03.2019.
 */
public class CombatComponent implements Component {

    private Weapon weapon;
    private Armour armour;

    public Weapon getWeapon() {
        return weapon;
    }

    public Armour getArmour() {
        return armour;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }
}
