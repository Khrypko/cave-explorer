package test.game.rpg.core.combat;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 12.03.2019.
 */
public class Stats implements Component {

    private int strenght;
    private int dexterity;

    public Stats(int strenght, int dexterity) {
        this.strenght = strenght;
        this.dexterity = dexterity;
    }

    public int getStrenght() {
        return strenght;
    }

    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
}
