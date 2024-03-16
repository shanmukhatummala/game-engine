package game.states;

import static game.map.MapLoader.loadMap;
import static game.map.MapValidator.isMapValid;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

public class PlaySetupPhase extends StartUpPhase {

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
    public void handleAssignCountries(Map p_map, GameEngine ge) {

        List<Player> players = p_map.getD_players();
        List<Country> countries = p_map.getD_countries();
        boolean countriesAssigned = p_map.assignCountries(players, countries);
        if (!countriesAssigned) {
            // it should throw an exception
            //            continue;
        }
        ge.setGamePhase(new IssueOrderPhase());
    }

    @Override
    public void handleEditMap(GameEngine ge) {
        ge.setGamePhase(new EditMapPhase());
    }

    @Override
    public void handleSaveMap(String p_fileName, Command p_command, Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't save a map here";
        printInvalidCommandMessage(message);
    }
}
