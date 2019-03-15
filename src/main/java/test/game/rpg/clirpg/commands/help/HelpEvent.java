package test.game.rpg.clirpg.commands.help;

import test.game.rpg.core.messaging.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maks on 14.03.2019.
 */
public class HelpEvent implements Event {

    public List<String> helpMessage(){
        List<String> lines = new ArrayList<>();

        lines.add("---- HELP ----");
        lines.add("-- Commands --");
        lines.add("");
        lines.add("- move [direction] - moves your character in specified direction");
        lines.add("  Possible options: north, north-east, east, south-east, south, south-west, west, north-west");
        lines.add("");
        lines.add("- attack [x:y] - performs attack on specified position. Notice, there is no point to swing a sword if no one is there...");
        lines.add("");
        lines.add("- save [save name] - Saves current progress. Please, do not use name 'back' for the save. Just don't");
        lines.add("");
        lines.add("- exit - leave the game. Nobody will blame you if you become bored playing...");
        lines.add("");
        lines.add("Press ENTER to continue");

        return lines;
    }

}
