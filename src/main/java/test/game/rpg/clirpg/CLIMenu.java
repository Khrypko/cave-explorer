package test.game.rpg.clirpg;

import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameFactory;
import test.game.rpg.core.Menu;

import java.util.List;
import java.util.Optional;

import static test.game.rpg.clirpg.renderer.GraphicsComponent.ANSI_BLUE;
import static test.game.rpg.clirpg.renderer.GraphicsComponent.ANSI_RESET;

/**
 * Created by Maks on 11.03.2019.
 */
public class CLIMenu implements Menu {

    private Terminal terminal;
    private GameFactory gameFactory;

    public CLIMenu(Terminal terminal, GameFactory gameFactory) {
        this.terminal = terminal;
        this.gameFactory = gameFactory;
    }

    @Override
    public void show() {
        terminal.clean();
        printMenu();
        receiveAndHandleCommand();
        terminal.clean();
    }

    private void receiveAndHandleCommand(){
        String command = null;
        while (command == null){
            command = CheckAndReceiveCommand();
            if (command == null){
                terminal.clean();
                printMenu();
                terminal.printLine("Please, enter correct command");
            }
        }

        executeCommand(command);
    }

    private void printMenu() {
        terminal.printLine(ANSI_BLUE + "Epic cave journey" + ANSI_RESET);
        terminal.printLine("");
        terminal.printLine("");

        terminal.printLine("1 - Start new game");
        terminal.printLine("2 - Load the game");
        terminal.printLine("q - Exit");
    }

    private String CheckAndReceiveCommand() {
        String command = terminal.readLine();
        switch (command){
            case "1":
            case "2":
            case "q":
                return command;
            default:
                return null;
        }
    }

    private void executeCommand(String command) {
        switch (command){
            case "1":
                startNewGame();
                break;
            case "2":
                loadGame();
                break;
            case "q":
                System.exit(0);

        }
    }

    private void startNewGame() {
        Game game = gameFactory.createGame();
        game.start();
    }

    private void loadGame() {
        List<String> saves = gameFactory.listOfSavedGames();
        terminal.printLine("Saved games:");
        saves.forEach(save -> terminal.printLine(" -" + save));

        String selectedSave = null;
        while (selectedSave == null){
            terminal.printLine("Please, select game to load, or type 'back' to return to previous menu");
            selectedSave = terminal.readLine();
        }

        if ("back".equals(selectedSave)){
            show();
            return;
        }

        Optional<Game> game = gameFactory.loadGame(selectedSave);
        if (game.isPresent()){
            game.get().start();
        } else {
            terminal.clean();
            printMenu();
            terminal.printLine("");
            terminal.printLine("Sorry, there is no game with such name...");
            receiveAndHandleCommand();
        }
    }


}
