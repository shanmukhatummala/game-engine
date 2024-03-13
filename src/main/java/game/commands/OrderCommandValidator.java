package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCommandValidator implements CommandValidator {
    static List<String> d_validCommands =
            Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");

    Map<String, Method> d_methodMap;

    public OrderCommandValidator() {
        d_methodMap = new HashMap<String, Method>();

        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                String orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        orderType,
                        OrderCommandValidator.class.getDeclaredMethod(
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
        List<String> l_commandArgs = p_command.getArgs();
        if (l_commandType.equals("showmap") && l_commandArgs.isEmpty()) {
            return true;
        }
        if (d_methodMap.containsKey(l_commandType)) {
            try {
                return (boolean) d_methodMap.get(l_commandType).invoke(this, p_command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean validateDeployCommand(Command p_command) {
        if (p_command.getArgs().size() != 2) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        try {
            int l_numArmies = Integer.parseInt(l_commandArgs.get(1));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean validateAdvanceCommand(Command p_command) {
        if (p_command.getArgs().size() != 3) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        try {
            int l_numArmies = Integer.parseInt(l_commandArgs.get(2));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean validateBombCommand(Command p_command) {
        return (p_command.getArgs().size() == 1);
    }

    private boolean validateBlockadeCommand(Command p_command) {
        return (p_command.getArgs().size() == 1);
    }

    private boolean validateAirliftCommand(Command p_command) {
        if (p_command.getArgs().size() != 3) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        try {
            int l_numArmies = Integer.parseInt(l_commandArgs.get(2));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean validateNegotiateCommand(Command p_command) {
        return (p_command.getArgs().size() == 1);
    }
}
