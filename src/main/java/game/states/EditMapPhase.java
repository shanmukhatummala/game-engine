package game.states;

import static game.map.MapSaver.saveMap;
import static game.map.MapValidator.isMapValid;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.io.BufferedReader;
import java.util.List;

public class EditMapPhase extends StartUpPhase {

    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't load a map here.";
        printInvalidCommandMessage(message);
    }


    @Override
    public void handleSaveMap(String p_fileName, Command p_command, Map p_map, GameEngine p_ge) {
        if (!p_fileName.equals(p_command.getArgs().get(0))) {
            System.out.println(
                    "The file name in 'savemap' command is different from the file you are editing.");
            System.out.println("Enter the right file name in save command!");
            //            continue;
        }
        if (!isMapValid(p_map)) {
            System.out.println("Current map is not valid: aborting the saving process.");
            //            continue;
        }
        saveMap(RESOURCES_PATH + p_fileName, p_map);
        // change phase
        //        break;
        p_ge.setGamePhase(new PlaySetupPhase());
    }

    @Override
    public void handleValidateMap(Map p_map) {
        if (isMapValid(p_map)) {
            System.out.println("The current map is valid!");
        } else {
            System.out.println("The current map isn't valid.");
        }
    }

    @Override
    public void handleAssignCountries(Map p_map, GameEngine p_ge) {
        String message = "Invalid Command in state" + this.getClass().getSimpleName();
        printInvalidCommandMessage(message);
    }


    @Override
    public void handleEditMap(GameEngine ge, Command p_command, Map p_map, BufferedReader p_bufferedReader) {
        String message = "Invalid Command you are already in the Edit Map mode";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't add or remove player while editing a map";
        printInvalidCommandMessage(message);
    }
}
