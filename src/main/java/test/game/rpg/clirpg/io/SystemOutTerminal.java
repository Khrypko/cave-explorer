package test.game.rpg.clirpg.io;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by Maks on 09.03.2019.
 */
public class SystemOutTerminal implements Terminal {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void clean() {
        System.out.print("\033[H\033[2J");
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void printLine(String line) {
        Charset utf8 = Charset.forName("UTF-8");
        try {
            PrintStream printStream = new PrintStream(System.out, true, utf8.name());
            printStream.println(line);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println(line);
    }

    @Override
    public void printLines(String[] lines) {
        for (String s : lines) {
            printLine(s);
        }
    }
}
