package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartUpCommandValidator implements CommandValidator {

    static List<String> d_validCommands =
            Arrays.asList("gameplayer", "showmap", "loadmap", "assigncountries", "editmap");

    Map<String, Method> d_methodMap;

    public StartUpCommandValidator() {

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

    private boolean validateGameplayerCommand(Command p_command) {
        if (p_command.getArgs().size() != 2) {
            return false;
        }
        List<String> l_commandArgs = p_command.getArgs();

        return (l_commandArgs.get(0).equals("-add") || l_commandArgs.get(0).equals("-remove"));
    }

    private boolean validateLoadmapCommand(Command p_command) {
        return (p_command.getArgs().size() == 1);
    }

    private boolean validateShowmapCommand(Command p_command) {
        return (p_command.getArgs().size() == 0);
    }

    private boolean validateAssigncountriesCommand(Command p_command) {
        return (p_command.getArgs().size() == 0);
    }

    private boolean validateEditmapCommand(Command p_command) {
        return (p_command.getArgs().size() == 1);
    }
}
