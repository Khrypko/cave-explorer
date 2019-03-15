package test.game.rpg.clirpg.map;

import test.game.rpg.core.GameObject;
import test.game.rpg.core.map.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Maks on 10.03.2019.
 */
public class CaveMap implements GameMap {

    private String id;
    private GameObject[][] map;
    private Vector size;
    private Map<String, GameObject> gameObjects = new HashMap<>();
    private World world;

    public CaveMap(String id, Vector size, World world) {
        this.world = world;
        this.id = id;
        this.size = size;
        this.map = new GameObject[size.getX()][size.getY()];
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<GameObject> getAllObjects() {
        return gameObjects.values().stream().collect(Collectors.toList());
    }

    @Override
    public GameObject getObjectOn(Vector coordinates) {
        if (coordinateOnMap(coordinates)){
            return map[coordinates.getX()][coordinates.getY()];
        }

        return null;
    }

    @Override
    public boolean isObjectOnMap(GameObject object) {
        return gameObjects.containsKey(object.getId());
    }

    @Override
    public void removeObjectFromMap(GameObject gameObject) {
        if (gameObjects.containsKey(gameObject.getId())){
            gameObjects.remove(gameObject.getId());

            Optional<TransformComponent> transform = gameObject.getComponent(TransformComponent.class);
            if (transform.isPresent()){
                Vector position = transform.get().getPosition();
                map[position.getX()][position.getY()] = null;
            }
        }
    }

    @Override
    public void moveObject(GameObject object, Vector newPosition) {
        GameObject objectOnMap = gameObjects.get(object.getId());
        if (objectOnMap != null){
            if (coordinateOnMap(newPosition)){
                if (coordinateEmpty(newPosition)){
                    Optional<TransformComponent> possibleTransform = objectOnMap.getComponent(TransformComponent.class);
                    possibleTransform.ifPresent(transform -> {
                        this.map[transform.getPosition().getX()][transform.getPosition().getY()] = null;
                        transform.setPosition(newPosition);
                        this.map[newPosition.getX()][newPosition.getY()] = objectOnMap;
                    });
                } else {
                    this.getObjectOn(newPosition).getComponent(Exit.class).ifPresent(exit -> this.world.changeMap());
                }
            }
        }
    }

    private boolean coordinateEmpty(Vector position) {
        return map[position.getX()][position.getY()] == null;
    }

    @Override
    public Vector size() {
        return new Vector(map.length, map[0].length);
    }

    private boolean coordinateOnMap(Vector coordinates) {
        if (coordinates.getX() < 0 || coordinates.getX() >= map.length
                || coordinates.getY() < 0 || coordinates.getY() >= map[0].length){
            return false;
        }

        return true;
    }

    public void putGameObjects(List<GameObject> gameObjects){
        clear();
        gameObjects.forEach(this::addObjectToMap);
    }

    private void clear() {
        this.gameObjects = new HashMap<>();
        this.map = new GameObject[this.size.getX()][this.size.getY()];
    }

    public void addObjectToMap(GameObject gameObject){
        gameObjects.put(gameObject.getId(), gameObject);
        Optional<TransformComponent> transform = gameObject.getComponent(TransformComponent.class);
        transform.ifPresent(t -> map[t.getPosition().getX()][t.getPosition().getY()] = gameObject);
    }


}
