package game.states;



import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;


import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.util.*;

 abstract class StartUpPhase implements Phase{

    public static final String RESOURCES_PATH = "src/main/resources/";



    @Override
    public void handleValidateMap(Map p_map) {

    }
}
