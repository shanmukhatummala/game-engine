package game.states;

import game.commands.Command;
import game.map.Map;

import static game.GameEngine.RESOURCES_PATH;
import static game.map.MapSaver.saveMap;
import static game.map.MapValidator.isMapValid;

public class EditMapPhase extends StartUpPhase{

    @Override
    public void handleSaveMap(String p_fileName, Command p_command, Map p_map) {
        if (!p_fileName.equals(p_command.getArgs().get(0))) {
            System.out.println(
                    "The file name in 'savemap' command is different from the file you are editing.");
            System.out.println("Enter the right file name in save command!");
//            continue;
        }
        if (!isMapValid(p_map)) {
            System.out.println(
                    "Current map is not valid: aborting the saving process.");
//            continue;
        }
        saveMap(RESOURCES_PATH + p_fileName, p_map);
        //change phase
//        break;
    }

    @Override
    public void handleValidateMap(Map p_map) {
        if (isMapValid(p_map)) {
            System.out.println("The current map is valid!");
        } else {
            System.out.println("The current map isn't valid.");
        }
    }

}
