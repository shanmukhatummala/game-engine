package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for validating the commands related to editing */
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

    /** Constructor for the Validator where the d_methodMap attribute is built */
    public EditCommandValidator() {

        d_methodMap = new HashMap<String, Method>();

        String l_orderType = null;

        // We create a map that links a valid command to its validating method
        // (e.g. savemap is linked to validateSavemapCommand)
        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                l_orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        l_orderType,
                        EditCommandValidator.class.getDeclaredMethod(
                                "validate"
                                        + l_orderType.substring(0, 1).toUpperCase()
                                        + l_orderType.substring(1)
                                        + "Command",
                                Command.class));
            } catch (NoSuchMethodException l_e) {
                l_e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Validates the editcontinent command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateEditcontinentCommand(Command p_command) {
        if (p_command.getD_args().size() < 1) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

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

    /**
     * Validates the editcountry command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateEditcountryCommand(Command p_command) {
        if (p_command.getD_args().size() < 1) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        if (l_commandArgs.get(0).equals("-add")) {
            return (l_commandArgs.size() == 3);
        }
        if (l_commandArgs.get(0).equals("-remove")) {
            return (l_commandArgs.size() == 2);
        }

        return false;
    }

    /**
     * Validates the editneighbor command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateEditneighborCommand(Command p_command) {
        if (p_command.getD_args().size() < 1) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        if (l_commandArgs.get(0).equals("-add") || l_commandArgs.get(0).equals("-remove")) {
            return (l_commandArgs.size() == 3);
        }

        return false;
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
     * Validates the savemap command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateSavemapCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * Validates the validatemap command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateValidatemapCommand(Command p_command) {
        return (p_command.getD_args().size() == 0);
    }
}
