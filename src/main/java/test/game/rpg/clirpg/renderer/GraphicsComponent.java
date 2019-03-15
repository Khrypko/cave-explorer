package test.game.rpg.clirpg.renderer;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 10.03.2019.
 */
public class GraphicsComponent implements Component {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private String symbolToRender;
    private String color;

    public GraphicsComponent(String symbolToRender, String color) {
        this.symbolToRender = symbolToRender;
        this.color = color;
    }

    public String toRender(){
        return color + symbolToRender + ANSI_RESET;
    }
}
