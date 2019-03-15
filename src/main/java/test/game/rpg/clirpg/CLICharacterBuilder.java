package test.game.rpg.clirpg;

import test.game.rpg.core.character.Experience;
import test.game.rpg.core.character.GenderComponent;
import test.game.rpg.core.character.charClass.ClassComponent;
import test.game.rpg.core.character.charClass.Warrior;
import test.game.rpg.core.character.race.RaceComponent;
import test.game.rpg.core.combat.CombatComponent;
import test.game.rpg.core.combat.Health;
import test.game.rpg.core.combat.Stats;
import test.game.rpg.core.combat.Treat;
import test.game.rpg.core.combat.armour.Armour;
import test.game.rpg.core.combat.armour.ArmourType;
import test.game.rpg.core.combat.command.DeathEvent;
import test.game.rpg.core.combat.weapon.Weapon;
import test.game.rpg.core.combat.weapon.WeaponType;
import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.clirpg.renderer.GraphicsComponent;
import test.game.rpg.core.CharacterBuilder;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.messaging.Messenger;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

/**
 * Created by Maks on 12.03.2019.
 */
public class CLICharacterBuilder implements CharacterBuilder {

    private Terminal terminal;
    private Messenger messenger;

    private GameObject character;

    public CLICharacterBuilder(Terminal terminal, Messenger messenger) {
        this.terminal = terminal;
        this.messenger = messenger;
    }

    @Override
    public GameObject createCharacter(String name) {
        this.character = new GameObject(name);

        selectRace();
        selectGender();
        selectClass();

        Experience experience = new Experience(this.character);
        this.messenger.subscribe(DeathEvent.class, experience);
        this.character.addComponent(experience);

        return character;
    }

    private void selectRace() {
        terminal.printLines(new String[]{
                "Please, select a race:",
                "   - human",
                "   - elf",
                "   - dwarf"
            }
        );

        String race = readUserInput("race", this::validateRace);
        switch (race){
            case "human":
                this.character.addComponent(new RaceComponent("human"));
                this.character.addComponent(new GraphicsComponent("♟ ", GraphicsComponent.ANSI_GREEN));
                this.character.addComponent(new Health(20));
                this.character.addComponent(new Stats(2, 2));
                this.character.addComponent(new Treat(2, 2));
                addCombatComponent(sword(), chainmail());
                break;
            case "elf":
                this.character.addComponent(new RaceComponent("elf"));
                this.character.addComponent(new GraphicsComponent("♝ ", GraphicsComponent.ANSI_GREEN));
                this.character.addComponent(new Health(15));
                this.character.addComponent(new Stats(2, 3));
                this.character.addComponent(new Treat(2, 2));
                addCombatComponent(sword(), gambeson());
                break;
            case "dwarf":
                this.character.addComponent(new RaceComponent("dwarf"));
                this.character.addComponent(new GraphicsComponent("♜ ", GraphicsComponent.ANSI_GREEN));
                this.character.addComponent(new Health(25));
                this.character.addComponent(new Stats(3, 1));
                this.character.addComponent(new Treat(2, 3));
                addCombatComponent(axe(), chainmail());
                break;
        }

    }

    private Armour gambeson() {
        return new Armour(ArmourType.PADDED, "gambeson", 1);
    }

    private Armour chainmail() {
        return new Armour(ArmourType.MAIL, "chain mail", 2);
    }

    private void addCombatComponent(Weapon weapon, Armour armour) {
        CombatComponent combatComponent = new CombatComponent();
        combatComponent.setWeapon(weapon);
        combatComponent.setArmour(armour);
        this.character.addComponent(combatComponent);
    }

    private Weapon sword() {
        return new Weapon(WeaponType.SWORD, "Arming Sword", Vector.with(2, 8), 2, Arrays.asList(19, 20));
    }

    private Weapon axe(){
        return new Weapon(WeaponType.AXE, "Dane Axe", Vector.with(2, 10), 3, Collections.singletonList(20));
    }

    private String readUserInput(String selectType, Function<String, String> validator){
        String command = null;
        while (command == null){
            String input = terminal.readLine();
            command = validator.apply(input);
            if (command == null){
                terminal.printLine("Please, select proper " + selectType);
            }
        }
        return command;
    }

    private String validateRace(String raceCode) {
        if ("human".equals(raceCode) || "elf".equals(raceCode) || "dwarf".equals(raceCode)){
            return raceCode;
        }

        return null;
    }

    private void selectGender() {
        terminal.printLines(new String[]{
                        "Please, select a gender:",
                        "   - man",
                        "   - woman"
                }
        );

        String gender = readUserInput("gender", this::validateGender);
        switch (gender){
            case "woman":
                this.character.addComponent(new GenderComponent("woman"));
                break;
            case "man":
                this.character.addComponent(new GenderComponent("man"));
                break;
        }
    }

    private String validateGender(String gender){
        if ("woman".equals(gender) || "man".equals(gender)){
            return gender;
        }

        return null;
    }

    private void selectClass() {
        terminal.printLines(new String[]{
                        "Please, select a class:",
                        "   - warrior"
                }
        );

        //TODO: add classes
        readUserInput("class", this::validateClass);
        this.character.addComponent(new ClassComponent(new Warrior()));
    }

    private String validateClass(String characterClass){
        if ("warrior".equals(characterClass)){
            return characterClass;
        }

        return null;
    }
}
