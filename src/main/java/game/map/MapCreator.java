package game.map;

import static game.map.MapEditor.editMap;

import java.io.File;
import java.io.IOException;

public class MapCreator {

    public static void createNewMap(String fileName) {

        try {
            boolean fileCreated = new File("src/main/resources/" + fileName).createNewFile();
            if (!fileCreated) {
                throw new IllegalStateException("File with same name already exists");
            }
            editMap(fileName);
            System.out.println("Enter commands to add/remove countries and continents");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
