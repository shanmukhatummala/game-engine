package game.util;

import game.commands.Command;
import game.map.Map;

/** Helper class for the issueOrder method of the Player class */
public class IssueOrderHelper {

    /** The map where the game is being played. Needed to retrieve info about other countries */
    public static Map Map;

    /** Command that is to be issued by the player */
    public static Command Command;

    /**
     * Gives the map which can used when Player creates Order
     *
     * @return map
     */
    public static Map getMap() {
        return Map;
    }

    /**
     * Gives the command which can used when Player creates Order
     *
     * @return command
     */
    public static Command getCommand() {
        return Command;
    }

    /**
     * Sets the map which can used when Player creates Order
     *
     * @param p_map The map where the game is played
     */
    public static void setMap(Map p_map) {
        Map = p_map;
    }

    /**
     * Sets the command which can used when Player creates Order
     *
     * @param p_command The command to be executed in issueOrder
     */
    public static void setCommand(Command p_command) {
        Command = p_command;
    }
}
