package game.states;

import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.util.*;

public class StartUpPhase implements Phase {

    public static final String RESOURCES_PATH = "src/main/resources/";

    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine ge) {
        loadMap(RESOURCES_PATH + p_command.getArgs().get(0), p_map);
        if (!isMapValid(p_map)) {
            System.out.println("The loaded map is invalid, please load a valid map.");
            p_map.clearMap();
            return;
        }
        ge.setGamePhase(new PlaySetupPhase());
    }

    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        for (Command l_command : p_commandList) {
            List<String> l_commandArgs = l_command.getArgs();
            if (l_commandArgs.get(0).equals("-add")) {
                p_map.addPlayer(l_commandArgs.get(1));
                System.out.println("Player " + l_commandArgs.get(1) + " added");
            } else {
                p_map.removePlayer(l_commandArgs.get(1));
                System.out.println("Player " + l_commandArgs.get(1) + " removed");
            }
        }
    }

    @Override
    public void handleEditMap() {}

    @Override
    public void handleShowMap(Map p_map) {
        showMap(p_map);
    }

    @Override
    public void handleAssignCountries(Map p_map) {}

    @Override
    public void handleSaveMap(String p_fileName, Command p_command, Map p_map) {}

    @Override
    public void handleValidateMap(Map p_map) {}
}
