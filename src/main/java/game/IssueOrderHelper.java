package game;

import game.commands.Command;
import game.map.Map;

public class IssueOrderHelper {

    public static Map Map;
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

    /** Sets the map which can used when Player creates Order */
    public static void setMap(Map map) {
        Map = map;
    }

    /** Sets the command which can used when Player creates Order */
    public static void setCommand(Command command) {
        Command = command;
    }
}
