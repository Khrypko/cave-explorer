package test.game.rpg.core.map;

import test.game.rpg.core.Game;
import test.game.rpg.core.component.GameSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maks on 10.03.2019.
 * System, that handles movement on the map
 */
public class TransformSystem implements GameSystem {

    private Game game;
    private List<MoveEvent> moveCommands = new ArrayList<>();

    @Override
    public void run() {
        GameMap map = game.getWorld().getCurrentMap();
        moveCommands.forEach(command -> map.moveObject(command.getObject(), command.getCoordinates()));
        moveCommands.clear();
    }

    @Override
    public void initialize(Game game) {
        this.game = game;
        this.game.getMessenger().subscribe(MoveEvent.class, command -> moveCommands.add(command));
    }
}
