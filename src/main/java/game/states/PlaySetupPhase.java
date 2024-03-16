package game.states;

import static game.map.MapLoader.loadMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

public class PlaySetupPhase extends StartUpPhase {

    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge) {
        loadMap(RESOURCES_PATH + p_command.getArgs().get(0), p_map);
        if (!isMapValid(p_map)) {
            System.out.println("The loaded map is invalid, please load a valid map.");
            p_map.clearMap();
            return;
        }
        p_ge.setGamePhase(new PlaySetupPhase());
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
    public void handleAssignCountries(Map p_map, GameEngine p_ge) {

        List<Player> players = p_map.getD_players();
        List<Country> countries = p_map.getD_countries();
        boolean countriesAssigned = p_map.assignCountries(players, countries);
        if (!countriesAssigned) {
            System.out.println("try again.");
            return;

        }
        System.out.println("Countries have been assigned.");
        System.out.println("You have entered the play mode.");
        p_ge.setGamePhase(new IssueOrderPhase());
    }



    @Override
    public void handleEditMap(GameEngine ge, Command p_command, Map p_map) {
        String l_fileName = p_command.getArgs().get(0);
        String l_filePath = RESOURCES_PATH + l_fileName;
        if (!fileExists(l_filePath)) {
            createNewFileForMap(l_filePath);
        } else {
            loadMap(l_filePath, p_map);
        }
        ge.setGamePhase(new EditMapPhase());
        System.out.println("You have entered the editing mode.");

    }

    @Override
    public void handleSaveMap(Command p_command, Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't save a map here";
        printInvalidCommandMessage(message);
    }


    @Override
    public void handleValidateMap(Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map here";
        printInvalidCommandMessage(message);
    }
    @Override
    public void handleEditCountriesOrContinentOrNeighbor(String[] p_args, Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't edit map while not in the edit mode phase";
        printInvalidCommandMessage(message);
    }

}
