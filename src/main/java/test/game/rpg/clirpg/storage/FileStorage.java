package test.game.rpg.clirpg.storage;

import test.game.rpg.core.Game;
import test.game.rpg.core.GameFactory;
import test.game.rpg.core.storage.GameContainer;
import test.game.rpg.core.storage.GameStorage;
import test.game.rpg.core.map.GameMap;
import test.game.rpg.core.storage.MapData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Maks on 14.03.2019.
 */
public class FileStorage implements GameStorage {

    private static final String DATA_PATH ="./saves/";

    @Override
    public boolean save(String name, Game game) {
        DataContainer dataContainer = new DataContainer();
        dataContainer.setName(game.getName());
        dataContainer.setPlayer(game.getPlayer());
        dataContainer.setMapData(extractMapData(game.getWorld().getCurrentMap()));
        return writeToFile(name, dataContainer);
    }

    private boolean writeToFile(String name, DataContainer dataContainer) {
        try {
            FileOutputStream fos = new FileOutputStream(DATA_PATH + name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dataContainer);
            oos.flush();
            oos.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    private MapData extractMapData(GameMap currentMap) {
        MapData mapData = new MapData();
        mapData.setMapId(currentMap.getId());
        mapData.setSize(currentMap.size());
        mapData.setGameObjects(currentMap.getAllObjects());
        return mapData;
    }

    @Override
    public Optional<GameContainer> load(String name) {
        try {
            FileInputStream fis = new FileInputStream(DATA_PATH + name);
            ObjectInputStream oin = new ObjectInputStream(fis);
            GameContainer container = (GameContainer) oin.readObject();
            return Optional.of(container);
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<String> listOfSavedFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_PATH))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.toFile().getName())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
