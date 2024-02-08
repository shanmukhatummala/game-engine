package game.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapEditor {

    public static void editMap(String fileName) {

        String path = "src/main/resources/" + fileName;
        File file = new File(path);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                System.out.println("Enter commands to add/remove countries and continents");
                String command = reader.readLine();
                validateEditCommand();
                // edit commands are of 2 types -- add or remove
                // edit commands would be implemented by Joyjit and Naveen. Leaving this for them.
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void validateEditCommand() {

    }
}
