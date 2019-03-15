package test.game.rpg.core;

import test.game.rpg.core.combat.command.DeathEvent;
import test.game.rpg.core.storage.SaveEvent;
import test.game.rpg.core.messaging.Message;
import test.game.rpg.core.engine.GameEngine;
import test.game.rpg.core.exception.GameNotInitializedProperly;
import test.game.rpg.core.map.World;
import test.game.rpg.core.messaging.Messenger;
import test.game.rpg.core.storage.GameStorage;

/**
 * Created by Maks on 09.03.2019.
 * Acts like an Context and responsible for keeping game state
 */
public class Game {

    private String name;
    private boolean running;
    private GameEngine engine;
    private GameStorage storage;
    private World world;
    private Messenger messenger;
    private GameObject player;

    private Game() {
    }

    public void start(){
        this.running = true;
        this.engine.initialize(this);
        this.initializeSubscribers();

        this.engine.run();
    }

    private void initializeSubscribers() {
        this.messenger.subscribe(SaveEvent.class, this::save);
        this.messenger.subscribe(DeathEvent.class, this::onGameObjectDeath);
    }

    private void save(SaveEvent command){
        boolean saved = this.storage.save(command.getSaveName(), this);
        String message = saved ? "Game successfully saved!" : "Error saving the game. Sorry =(";
        this.messenger.send(new Message(message));
    }

    private void onGameObjectDeath(DeathEvent e){
        if (e.getDeadObject().equals(player)){
            messenger.send(new Message("Game Over! You was killed by cruel monster!"));
            this.stop();
        }
    }

    public void stop(){
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public GameObject getPlayer() {
        return player;
    }

    public static GameBuilder builder(){
        return new GameBuilder();
    }

    public static class GameBuilder{
        private Game game = new Game();

        public GameBuilder name(String name){
            game.name = name;
            return this;
        }

        public GameBuilder engine(GameEngine engine){
            game.engine = engine;
            return this;
        }

        public GameBuilder storage(GameStorage storage){
            game.storage = storage;
            return this;
        }

        public GameBuilder world(World world){
            game.world = world;
            return this;
        }

        public GameBuilder messenger(Messenger messenger){
            game.messenger = messenger;
            return this;
        }

        public GameBuilder player(GameObject player){
            game.player = player;
            return this;
        }

        public Game build(){
            validateMandatoryParams();
            return game;
        }

        private void validateMandatoryParams() {
            if (game.name == null || game.storage == null || game.engine == null || game.world == null || game.messenger == null || game.player == null){
                throw new GameNotInitializedProperly("Name, Storage or game Engine was not specified");
            }
        }

        private GameBuilder() {
        }
    }
}
