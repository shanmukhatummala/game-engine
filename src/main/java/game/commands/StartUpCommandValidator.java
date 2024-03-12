package game.commands;

import game.GameEngine;
import game.pojo.Player;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartUpCommandValidator implements CommandValidator {

    static List<String> d_validCommands =
            Arrays.asList("gameplayer", "showmap", "loadmap", "assigncountries");

    Map<String, Method> d_methodMap;
    GameEngine d_engine;

    public StartUpCommandValidator(GameEngine p_engine) {
        d_engine = p_engine;
        d_methodMap = new HashMap<String, Method>();

        String orderType = null;

        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        orderType,
                        StartUpCommandValidator.class.getDeclaredMethod(
                                "validate"
                                        + orderType.substring(0, 1).toUpperCase()
                                        + orderType.substring(1)
                                        + "Command",
                                Command.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean validate(Command p_command) throws IllegalArgumentException {
        String l_commandType = p_command.getCommandType();
        if (d_methodMap.containsKey(l_commandType)) {
            try {
                return (boolean) d_methodMap.get(l_commandType).invoke(this, p_command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean validateGameplayerCommand(Command p_command) {
        if (p_command.getArgs().size() != 2) {
            return false;
        }
        List<Player> l_players = d_engine.getD_map().getD_players();
        List<String> l_commandArgs = p_command.getArgs();

        if (l_commandArgs.get(0).equals("-add")) {
            if (l_players.size() == 0) {
                return true;
            }
            return (l_players.stream()
                    .noneMatch(l_player -> l_player.getD_name().equals(l_commandArgs.get(1))));
        }
        if (l_commandArgs.get(0).equals("-remove")) {
            return (l_players.stream()
                    .anyMatch(l_player -> l_player.getD_name().equals(l_commandArgs.get(1))));
        }

        return false;
    }

    public boolean validateLoadmapCommand(Command p_command) {
        // load map command validation
        return true;
    }

    public boolean validateShowmapCommand(Command p_command) {
        // load map command validation
        return true;
    }

    public boolean validateAssigncountriesCommand(Command p_command) {
        // load map command validation
        return true;
    }
}
