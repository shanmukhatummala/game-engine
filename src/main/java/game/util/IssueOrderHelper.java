package game.util;

import game.commands.Command;
import game.map.Map;
import game.order.Deploy;
import game.pojo.Country;
import game.pojo.Player;

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
    public static void setMap(Map p_map) {
        Map = p_map;
    }

    /** Sets the command which can used when Player creates Order */
    public static void setCommand(Command p_command) {
        Command = p_command;
    }




    public static boolean validateDeploy(Player p_player, Country p_destination, int p_armiesNumber) {
        if (!p_player.getD_countries().contains(p_destination)) {
            System.out.println(p_destination + " is not owned by " + p_player);
            return false;
        }

        if (p_armiesNumber < 0
                || p_armiesNumber > p_player.getD_reinforcements()) {
            System.out.println("Invalid number of armies");
            return false;
        }

        return true;
    }

}
