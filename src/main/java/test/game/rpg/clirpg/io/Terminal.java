package test.game.rpg.clirpg.io;

/**
 * Created by Maks on 09.03.2019.
 * Abstracts how cli i/o is implemented and decouples other components from it;
 */
public interface Terminal {

    void clean();

    String readLine();

    void printLine(String line);

    void printLines(String[] lines);

}
