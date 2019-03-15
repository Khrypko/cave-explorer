package test.game.rpg.clirpg;

import test.game.rpg.core.combat.CombatSystem;
import test.game.rpg.clirpg.commands.combat.AttackCommandParser;
import test.game.rpg.clirpg.commands.exit.ExitCommandParser;
import test.game.rpg.clirpg.commands.help.HelpCommandParser;
import test.game.rpg.clirpg.commands.save.SaveCommandParser;
import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.clirpg.io.TerminalFactory;
import test.game.rpg.clirpg.map.DummyGameWorld;
import test.game.rpg.core.map.TransformSystem;
import test.game.rpg.core.npc.NpcSystem;
import test.game.rpg.clirpg.player.PlayerController;
import test.game.rpg.clirpg.storage.FileStorage;
import test.game.rpg.core.map.World;
import test.game.rpg.clirpg.commands.CommandFactory;
import test.game.rpg.clirpg.player.command.move.MoveCommandParser;
import test.game.rpg.clirpg.renderer.ConsoleRenderer;
import test.game.rpg.core.*;
import test.game.rpg.core.component.GameSystem;
import test.game.rpg.core.engine.GameEngine;
import test.game.rpg.core.messaging.Messenger;
import test.game.rpg.core.storage.GameStorage;
import test.game.rpg.core.storage.MapData;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Maks on 10.03.2019.
 */
public class CLIGameFactory implements GameFactory {

    private CharacterBuilder characterBuilder;
    private Terminal terminal;
    private CommandFactory commandFactory;
    private Messenger messenger;
    private GameStorage gameStorage;

    public CLIGameFactory(Terminal terminal, GameStorage gameStorage, CharacterBuilder characterBuilder) {
        this.terminal = terminal;
        this.gameStorage = gameStorage;
        this.messenger = new Messenger();
        this.characterBuilder = characterBuilder;
        this.commandFactory = new CommandFactory();
        registerPossibleCommands();
    }

    private void registerPossibleCommands() {
        this.commandFactory.register(new MoveCommandParser());
        this.commandFactory.register(new AttackCommandParser());

        this.commandFactory.register(new HelpCommandParser());
        this.commandFactory.register(new ExitCommandParser());
        this.commandFactory.register(new SaveCommandParser());
    }

    @Override
    public Game createGame() {
        GameObject player = characterBuilder.createCharacter(askUserForName());
        return Game.builder()
                .name(UUID.randomUUID().toString())
                .engine(new GameEngine(configureSystems()))
                .storage(gameStorage)
                .messenger(this.messenger)
                .world(createWorld(player))
                .player(player)
                .build();

    }

    private World createWorld(GameObject player) {
        return new DummyGameWorld(player, messenger);
    }

    private String askUserForName() {
        terminal.printLine("Please, provide a name for the character");
        return terminal.readLine();
    }

    private List<GameSystem> configureSystems() {
        List<GameSystem> systems = new LinkedList<>();

        systems.add(new ConsoleRenderer(terminal));
        systems.add(new PlayerController(terminal, commandFactory));
        systems.add(new NpcSystem());
        systems.add(new CombatSystem());
        systems.add(new TransformSystem());
        systems.add(new ConsoleRenderer(terminal));


        return systems;
    }

    @Override
    public Optional<Game> loadGame(String gameId) {
        return this.gameStorage.load(gameId).map(gameContainer -> Game.builder()
                .player(gameContainer.getPlayer())
                .messenger(this.messenger)
                .engine(new GameEngine(configureSystems()))
                .name(gameContainer.getName())
                .storage(this.gameStorage)
                .world(loadWorld(gameContainer.getPlayer(), gameContainer.getMapData()))
                .build());
    }

    @Override
    public List<String> listOfSavedGames() {
        return gameStorage.listOfSavedFiles();
    }

    private World loadWorld(GameObject player, MapData currentMap) {
        return DummyGameWorld.loadWorld(player, currentMap);
    }
}
