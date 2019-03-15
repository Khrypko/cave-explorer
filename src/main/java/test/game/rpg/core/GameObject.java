package test.game.rpg.core;

import test.game.rpg.core.component.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Maks on 10.03.2019.
 * Class that represents any object in the game. Used as container for functional components
 */
public class GameObject implements Serializable{

    private String id;
    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    public GameObject(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public <T extends Component> Optional<T> getComponent(Class<T> type){
        Component component = components.get(type);
        if (component == null){
            return Optional.empty();
        }
        return Optional.of(type.cast(component));
    }

    public GameObject addComponent(Component component){
        this.components.put(component.getClass(), component);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
