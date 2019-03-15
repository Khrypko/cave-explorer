package test.game.rpg.core.map;

import test.game.rpg.core.component.Component;

/**
 * Created by Maks on 10.03.2019.
 */
public class TransformComponent implements Component {

    private Vector position;

    public TransformComponent(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }
}
