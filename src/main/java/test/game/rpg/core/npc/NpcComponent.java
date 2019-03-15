package test.game.rpg.core.npc;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameObject;
import test.game.rpg.core.component.Component;
import test.game.rpg.core.messaging.Event;

import java.util.Optional;

/**
 * Created by Maks on 13.03.2019.
 * Component, that indicates that game object is actually some NPC
 */
public class NpcComponent implements Component {

    private NpcAI npcAI;
    private GameObject npc;

    public NpcComponent(NpcAI npcAI, GameObject npc) {
        this.npcAI = npcAI;
        this.npc = npc;
    }

    public Optional<Event> simulateTurn(Game game){
        return npcAI.simulateTurn(npc, game);
    }
}
