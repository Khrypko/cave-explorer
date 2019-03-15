package test.game.rpg.core.character.charClass;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 12.03.2019.
 */
public class ClassComponent implements Component {

    private CharacterClass characterClass;

    public ClassComponent(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }
}
