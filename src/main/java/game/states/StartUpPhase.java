package game.states;




import static game.map.MapEditor.editMap;
import static game.map.MapHelper.playerOwnsContinent;
import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Continent;
import game.pojo.Country;
import game.pojo.Player;
import game.states.Phase;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class StartUpPhase implements Phase{
    public static final String RESOURCES_PATH = "src/main/resources/";
    @Override
    public void loadMapHandler(Command p_command, Map p_map) {
        loadMap(RESOURCES_PATH + p_command.getArgs().get(0), p_map);
        if (!isMapValid(p_map)) {
            System.out.println(
                    "The loaded map is invalid, please load a valid map.");
            p_map.clearMap();
        }
    }

    @Override
    public void gamePlayerHandler() {

    }

    @Override
    public void editMapHandler() {

    }

    @Override
    public void showMapHandler(Map p_map) {
        showMap(p_map);
    }

    @Override
    public void assignCountriesHandler() {

    }
}
