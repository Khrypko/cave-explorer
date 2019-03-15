package test.game.rpg.core.character.race;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 12.03.2019.
 */
public class RaceComponent implements Component {

    private String race;

    public RaceComponent(String race) {
        this.race = race;
    }

    public String getRace() {
        return race;
    }
}
