package test.game.rpg.clirpg.player;

import test.game.rpg.clirpg.commands.help.HelpEvent;
import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.clirpg.commands.CommandFactory;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.component.GameSystem;
import test.game.rpg.core.messaging.Event;
import test.game.rpg.core.messaging.Messenger;

import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 */
public class PlayerController implements GameSystem {

    private Game game;
    private Terminal terminal;
    private CommandFactory commandFactory;
    private Messenger messenger;
    private GameObject player;

    public PlayerController(Terminal terminal, CommandFactory commandFactory) {
        this.terminal = terminal;
        this.commandFactory = commandFactory;
    }

    @Override
    public void run() {
        String input = terminal.readLine();
        Optional<Event> command = commandFactory.parseCommand(input, player, game);
        command.ifPresent(messenger::send);
    }

    @Override
    public void initialize(Game game) {
        this.game = game;
        this.messenger = game.getMessenger();
        this.player = game.getPlayer();

        this.messenger.subscribe(HelpEvent.class, this::handleHelp);
    }

    private void handleHelp(HelpEvent command) {
        command.helpMessage().forEach(terminal::printLine);
        terminal.readLine();
    }
}
