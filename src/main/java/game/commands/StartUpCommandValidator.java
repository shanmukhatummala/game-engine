package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for validating the commands related to the startup phase */
public class StartUpCommandValidator implements CommandValidator {

    static List<String> d_validCommands =
            Arrays.asList(
                    "gameplayer", "showmap", "loadmap", "assigncountries", "editmap", "loadgame");

    Map<String, Method> d_methodMap;

    /** Constructor for the Validator where the d_methodMap attribute is built */
    public StartUpCommandValidator() {

        d_methodMap = new HashMap<String, Method>();

        String l_orderType = null;

        // We create a map that links a valid command to its validating method
        // (e.g. gameplayer is linked to validateGameplayerCommand)
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
                // call the method corresponding to the given command type
                return (boolean) d_methodMap.get(l_commandType).invoke(this, p_command);
            } catch (Exception l_e) {
                l_e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Validates the gameplayer command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateGameplayerCommand(Command p_command) {
        if (p_command.getD_args().size() != 2) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        return (l_commandArgs.get(0).equals("-add") || l_commandArgs.get(0).equals("-remove"));
    }

    /**
     * Validates the loadmap command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateLoadmapCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * Validates the showmap command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateShowmapCommand(Command p_command) {
        return (p_command.getD_args().size() == 0);
    }

    /**
     * Validates the assigncountries command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateAssigncountriesCommand(Command p_command) {
        return (p_command.getD_args().size() == 0);
    }

    /**
     * Validates the editmap command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateEditmapCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * @param p_command
     * @return
     */
    private boolean validateLoadgameCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }
}
