package test.game.rpg.core.engine;

import test.game.rpg.core.Game;
import test.game.rpg.core.component.GameSystem;

import java.util.List;

/**
 * Created by Maks on 09.03.2019.
 * Entire game build using pattern "object-component-system".
 * In short:
 *  - all game logic that should be handle during game loop is implemented in systems
 *  - each object in the game is represented by GameObject, that is basically just container for components
 *  - Components are used to keep data like position, visual representation, and so on. Used by the systems
 */
public class GameEngine {

    private Game game;
    private List<GameSystem> systems;

    public GameEngine(List<GameSystem> systems) {
        assert systems != null;
        this.systems = systems;
    }

    public void initialize(Game game){
        assert game != null;
        this.game = game;
        this.systems.forEach(system -> system.initialize(game));

    }

    public void run(){
        while (game.isRunning()){
            systems.forEach(GameSystem::run);
        }
    }

}
