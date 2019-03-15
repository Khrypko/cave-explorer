package test.game.rpg.core.npc;

import test.game.rpg.core.Game;
import test.game.rpg.core.component.GameSystem;
import test.game.rpg.core.messaging.Messenger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Maks on 13.03.2019.
 * System, that should process all NPC actions during the turn
 */
public class NpcSystem implements GameSystem{

    private Game game;
    private Messenger messenger;

    @Override
    public void run() {
        npcOnMap().stream()
                .map(npc -> npc.simulateTurn(game))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(messenger::send);
    }

    private List<NpcComponent> npcOnMap() {
        return game.getWorld().getCurrentMap().getAllObjects().stream()
                .map(obj -> obj.getComponent(NpcComponent.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public void initialize(Game game) {
        this.game = game;
        this.messenger = game.getMessenger();
    }

    public void test(){

    }
}
