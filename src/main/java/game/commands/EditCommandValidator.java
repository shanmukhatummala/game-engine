package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditCommandValidator implements CommandValidator {

    static List<String> d_validCommands =
            Arrays.asList(
                    "editcontinent",
                    "editcountry",
                    "editneighbor",
                    "showmap",
                    "savemap",
                    "validatemap");

    Map<String, Method> d_methodMap;

    public EditCommandValidator() {

        d_methodMap = new HashMap<String, Method>();

        String orderType = null;

        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        orderType,
                        EditCommandValidator.class.getDeclaredMethod(
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

    private boolean validateEditcontinentCommand(Command p_command) {
        if (p_command.getArgs().size() < 1) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        if (l_commandArgs.get(0).equals("-add")) {
            if (l_commandArgs.size() != 3) {
                return false;
            }
            try {
                Integer.parseInt(l_commandArgs.get(2));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        if (l_commandArgs.get(0).equals("-remove")) {
            return (l_commandArgs.size() == 2);
        }

        return false;
    }

    private boolean validateEditcountryCommand(Command p_command) {
        if (p_command.getArgs().size() < 1) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        if (l_commandArgs.get(0).equals("-add")) {
            return (l_commandArgs.size() == 3);
        }
        if (l_commandArgs.get(0).equals("-remove")) {
            return (l_commandArgs.size() == 2);
        }

        return false;
    }

    private boolean validateEditneighborCommand(Command p_command) {
        if (p_command.getArgs().size() < 1) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        if (l_commandArgs.get(0).equals("-add") || l_commandArgs.get(0).equals("-remove")) {
            return (l_commandArgs.size() == 3);
        }

        return false;
    }

    private boolean validateShowmapCommand(Command p_command) {
        return (p_command.getArgs().size() == 0);
    }

    private boolean validateSavemapCommand(Command p_command) {
        return (p_command.getArgs().size() == 1);
    }

    private boolean validateValidatemapCommand(Command p_command) {
        return (p_command.getArgs().size() == 0);
    }
}
