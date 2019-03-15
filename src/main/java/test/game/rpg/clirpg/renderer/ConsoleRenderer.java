package test.game.rpg.clirpg.renderer;

import test.game.rpg.core.character.Experience;
import test.game.rpg.core.combat.Health;
import test.game.rpg.clirpg.io.Terminal;
import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.component.GameSystem;
import test.game.rpg.core.map.Vector;
import test.game.rpg.core.map.GameMap;
import test.game.rpg.core.messaging.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 */
public class ConsoleRenderer implements GameSystem {

    private static final String EMPTY = GraphicsComponent.ANSI_BLUE + "\u2001 " + GraphicsComponent.ANSI_RESET;

    private Game game;
    private Terminal terminal;
    private List<String> messages = new LinkedList<>();

    public ConsoleRenderer(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void run() {
        terminal.clean();
        renderMenu();
        renderMap();
        renderMessages();
    }

    private void renderMenu() {
        renderPlayerName();
        renderPlayerHealth();
        renderPlayerExperience();
        terminal.printLine("Type 'help' to get more info");
    }

    private void renderPlayerName() {
        terminal.printLine(String.format("Player name: %s.", game.getPlayer().getId()));
    }

    private void renderPlayerHealth() {
        Optional<Health> health = game.getPlayer().getComponent(Health.class);
        String healthString = health.map(h -> String.format("Health: %s/%s", h.getCurrentHealth(), h.getMaxHealth())).orElse("No health... strange");
        terminal.printLine(healthString);
    }
    private void renderPlayerExperience() {
        Optional<Experience> experience = game.getPlayer().getComponent(Experience.class);
        String exp = experience.map(experience1 -> String.valueOf(experience1.getAmount())).orElse("");
        terminal.printLine(String.format("Experience: %sxp", exp));
    }

    private void renderMap() {
        GameMap currentMap = game.getWorld().getCurrentMap();
        Vector size = currentMap.size();
        renderTopIndexes(size);
        String[] renderedMap = new String[size.getX()];
        for (int i = 0; i < size.getX(); i++){
            StringBuilder lineToRender = new StringBuilder();
            if (i != 0 && i != size.getX() - 1){
                lineToRender.append(i);
            } else {
                lineToRender.append(" ");
            }
            for (int j = 0; j < size.getY(); j++){
                GameObject gameObject = currentMap.getObjectOn(Vector.with(i, j));
                if (gameObject == null){
                    lineToRender.append(EMPTY);
                } else {
                    Optional<GraphicsComponent> graphicComponent = gameObject.getComponent(GraphicsComponent.class);
                    lineToRender.append(graphicComponent.isPresent() ? graphicComponent.get().toRender() : EMPTY);
                }
            }
            renderedMap[i] = lineToRender.toString();
        }
        terminal.printLines(renderedMap);
    }

    private void renderTopIndexes(Vector size) {
        StringBuilder indexes = new StringBuilder();
        indexes.append("  ");
        for (int i = 0; i < size.getY(); i++){
            if (i != 0 && i != size.getY() - 1){
                indexes.append(" ");
                indexes.append(i);
                indexes.append(" ");
            }
        }
        terminal.printLine(indexes.toString());
    }

    private void renderMessages() {
        this.messages.forEach(terminal::printLine);
        this.messages.clear();
    }

    @Override
    public void initialize(Game game) {
        this.game = game;
        game.getMessenger().subscribe(Message.class, message -> messages.add(message.getMessage()));
    }

}
