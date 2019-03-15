package test.game.rpg.clirpg.commands.exit;

import test.game.rpg.core.messaging.Subscriber;

/**
 * Created by Maks on 12.03.2019.
 */
public class ExitCommandSubscriber implements Subscriber<ExitEvent> {
    @Override
    public void notify(ExitEvent command) {
        command.exit();
    }
}
