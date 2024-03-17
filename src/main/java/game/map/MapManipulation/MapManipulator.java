package game.map.MapManipulation;

import static game.constants.MapManipulation.*;

import game.map.Map;

/**
 * The MapManipulator class is responsible for processing commands related to map manipulation. It
 * delegates the processing of specific commands to corresponding processor classes.
 */
public class MapManipulator {

    /**
     * Processes the given command based on the arguments and the game p_map.
     *
     * @param p_args The command arguments.
     * @param p_map The game p_map on which the command will be executed.
     */
    public void processCommand(String[] p_args, Map p_map) {
        switch (p_args[0]) {
            case EDIT_CONTINENT:
                EditContinentProcessor.process(p_args, p_map);
                break;
            case EDIT_COUNTRY:
                EditCountryProcessor.process(p_args, p_map);
                break;
            case EDIT_NEIGHBOR:
                EditNeighborProcessor.process(p_args, p_map);
                break;
            default:
                System.out.println("Invalid command please try again");
        }
    }
}
