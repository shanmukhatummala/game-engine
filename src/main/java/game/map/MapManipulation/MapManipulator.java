package game.map.MapManipulation;

import static game.constants.MapManipulation.*;

import game.commands.Command;
import game.map.Map;

import java.util.List;

/**
 * The MapManipulator class is responsible for processing commands related to map manipulation. It
 * delegates the processing of specific commands to corresponding processor classes.
 */
public class MapManipulator {

    /**
     * Processes the given command based on the arguments and the game p_map.
     *
     * @param p_commandList The List of Command objects.
     * @param p_map The game p_map on which the command will be executed.
     */
    public void processCommand(List<Command> p_commandList, Map p_map) {
        String l_commandType = p_commandList.get(0).getD_commandType();
        String[] l_args =
                p_commandList.stream()
                        .flatMap(command -> command.getD_args().stream())
                        .toArray(String[]::new);

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
