package test.game.rpg.core.character;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 12.03.2019.
 */
public class GenderComponent implements Component {

    private String gender;

    public GenderComponent(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
