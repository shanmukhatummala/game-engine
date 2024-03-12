package game.commands;

import game.pojo.Player;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCommandValidator implements CommandValidator {
    List<String> d_validCommands =
            Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");

    Map<String, Method> d_methodMap;
    Player d_player;

    public OrderCommandValidator(Player p_player) {
        d_player = p_player;
        Map<String, Method> d_methodMap = new HashMap<String, Method>();

        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                String orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        orderType,
                        OrderCommandValidator.class.getMethod(
                                "validate"
                                        + orderType.substring(0, 1).toUpperCase()
                                        + orderType.substring(1)
                                        + "Order"));
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
        List<String> l_commandArgs = p_command.getArgs();
        if (l_commandType.equals("showmap") && l_commandArgs.isEmpty()) {
            return true;
        }
        if (d_methodMap.containsKey(l_commandType)) {
            try {
                return (boolean) d_methodMap.get(l_commandType).invoke(p_command.getArgs());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isCountryOwned(String p_countryName) {
        return (d_player.getD_countries().stream()
                .allMatch(l_country -> l_country.getD_name().equals(p_countryName)));
    }

    private boolean validateDeployOrder(List<String> p_commandArgs) {
        if (p_commandArgs.size() != 2) {
            return false;
        }
        String l_countryName = p_commandArgs.get(0);
        int l_numArmies = Integer.parseInt(p_commandArgs.get(1));

        return (isCountryOwned(l_countryName) && l_numArmies <= d_player.getD_reinforcements());
    }

    private boolean validateAdvanceOrder(List<String> p_commandArgs) {
        // editContinent command validation
        return true;
    }

    private boolean validateBombOrder(List<String> p_commandArgs) {
        // editNeighbor command validation
        return true;
    }

    private boolean validateBlockadeOrder(List<String> p_commandArgs) {
        // editMap command validation
        return true;
    }

    private boolean validateAirliftOrder(List<String> p_commandArgs) {
        // game player command validation
        return true;
    }

    private boolean validateNegotiateOrder(List<String> p_commandArgs) {
        // load map command validation
        return true;
    }
}
