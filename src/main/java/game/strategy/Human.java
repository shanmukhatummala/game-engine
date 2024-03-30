package game.strategy;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Human extends PlayerStrategy {

    private static Human d_humanStrategy;

    public static Human getHumanStrategy() {
        if (d_humanStrategy == null) {
            Human.d_humanStrategy = new Human();
        }
        return d_humanStrategy;
    }

    private Human() {}
    ;

    @Override
    public Command createOrder(Map p_map, Player p_player) throws IOException {
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
        String l_commandString = l_bufferedReader.readLine();
        Command l_command;
        try {
            l_command = CommandParser.parse(l_commandString).get(0);
            return l_command;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return this.createOrder(p_map, p_player);
        }
    }
}
