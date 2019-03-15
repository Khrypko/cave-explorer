package test.game.rpg;

import test.game.rpg.clirpg.CLICharacterBuilder;
import test.game.rpg.clirpg.CLIGameFactory;
import test.game.rpg.clirpg.CLIMenu;
import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.clirpg.io.TerminalFactory;
import test.game.rpg.clirpg.storage.FileStorage;
import test.game.rpg.core.CharacterBuilder;
import test.game.rpg.core.Menu;
import test.game.rpg.core.messaging.Messenger;
import test.game.rpg.core.storage.GameStorage;

import java.io.IOException;

/**
 * Created by Maks on 09.03.2019.
 * Entry point for the game.
 * Here main components are initialized and control passed to menu
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalFactory.console();
        GameStorage gameStorage = new FileStorage();
        Messenger messenger = new Messenger();
        CharacterBuilder characterBuilder = new CLICharacterBuilder(terminal, messenger);
        Menu menu = new CLIMenu(terminal, new CLIGameFactory(terminal, gameStorage, characterBuilder));

        menu.show();
    }

}
