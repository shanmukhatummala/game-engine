package game.map.MapManipulation;

import static game.constants.MapManipulation.*;

import game.commands.Command;
import game.map.Map;

/**
 * The MapManipulator class is responsible for processing commands related to map manipulation. It
 * delegates the processing of specific commands to corresponding processor classes.
 */
public class MapManipulator {

    /**
     * Processes the given command based on the arguments and the game p_map.
     *
     * @param p_command The Command object.
     * @param p_map The game p_map on which the command will be executed.
     */
    public void processCommand(Command p_command, Map p_map) {
        String l_commandType = p_command.getD_commandType();
        String[] l_args = p_command.getD_args().toArray(String[]::new);

        switch (l_commandType) {
            case EDIT_CONTINENT:
                EditContinentProcessor.process(l_args, p_map);
                break;
            case EDIT_COUNTRY:
                EditCountryProcessor.process(l_args, p_map);
                break;
            case EDIT_NEIGHBOR:
                EditNeighborProcessor.process(l_args, p_map);
                break;
            default:
                System.out.println("Invalid command please try again");
        }
    }
}
