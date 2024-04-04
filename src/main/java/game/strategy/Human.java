package game.strategy;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Human strategy, allows for a human player to play the game and create orders using the console.
 */
public class Human extends PlayerStrategy implements Serializable {

    /** Unique Human object. */
    private static Human d_humanStrategy;

    /**
     * Provides access to the strategy, using the Singleton design pattern
     *
     * @return the human strategy
     */
    public static Human getHumanStrategy() {
        if (d_humanStrategy == null) {
            Human.d_humanStrategy = new Human();
        }
        return d_humanStrategy;
    }

    /** Private constructor to make sure that the client calls getHumanStrategy() */
    private Human() {}
    ;

    /** Prompts the user to input a command to create an order */
    @Override
    public Command createOrder(Map p_map, Player p_player) {
        System.out.println(
                "Player: "
                        + p_player.getD_name()
                        + ", enter the command "
                        + "(reinforcements available before the start of this round: "
                        + p_player.getD_reinforcements()
                        + (p_player.getD_cards().isEmpty()
                                ? ""
                                : " and cards available before the start of this round: "
                                        + p_player.getD_cards())
                        + "):");
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Command l_command;
        try {
            String l_commandString = l_bufferedReader.readLine();
            l_command = CommandParser.parse(l_commandString).get(0);
            return l_command;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error when reading command. Error message: " + e.getMessage());
        }
        return this.createOrder(p_map, p_player);
    }
}
