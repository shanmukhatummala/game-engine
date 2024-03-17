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

        String l_orderType = null;

        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                l_orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        l_orderType,
                        StartUpCommandValidator.class.getDeclaredMethod(
                                "validate"
                                        + l_orderType.substring(0, 1).toUpperCase()
                                        + l_orderType.substring(1)
                                        + "Command",
                                Command.class));
            } catch (NoSuchMethodException | SecurityException l_e) {
                l_e.printStackTrace();
            }
        }
    }

    @Override
    public boolean validate(Command p_command) throws IllegalArgumentException {
        String l_commandType = p_command.getD_commandType();
        if (d_methodMap.containsKey(l_commandType)) {
            try {
                return (boolean) d_methodMap.get(l_commandType).invoke(this, p_command);
            } catch (Exception l_e) {
                l_e.printStackTrace();
            }
        }
        return false;
    }

    private boolean validateGameplayerCommand(Command p_command) {
        if (p_command.getD_args().size() != 2) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        return (l_commandArgs.get(0).equals("-add") || l_commandArgs.get(0).equals("-remove"));
    }

    private boolean validateLoadmapCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    private boolean validateShowmapCommand(Command p_command) {
        return (p_command.getD_args().size() == 0);
    }

    private boolean validateAssigncountriesCommand(Command p_command) {
        return (p_command.getD_args().size() == 0);
    }

    private boolean validateEditmapCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }
}
