package test.game.rpg.clirpg.io;

import java.nio.charset.Charset;

/**
 * Created by Maks on 09.03.2019.
 */
public class ConsoleTerminal implements Terminal {

    @Override
    public void clean() {
        System.out.print("\033[H\033[2J");
    }

    @Override
    public String readLine() {
        return System.console().readLine();
    }

    @Override
    public void printLine(String line) {
        System.console().writer().println(line);
    }

    @Override
    public void printLines(String[] lines) {
        for (String s : lines) {
            System.console().writer().println(s);
        }
    }
}
