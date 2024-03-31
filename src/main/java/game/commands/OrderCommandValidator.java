package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for validating the commands related to giving orders */
public class OrderCommandValidator implements CommandValidator {
    static List<String> d_validCommands =
            Arrays.asList(
                    "deploy", "advance", "bomb", "blockade", "airlift", "negotiate", "commit", "savegame");

    Map<String, Method> d_methodMap;

    /** Constructor for the Validator where the d_methodMap attribute is built */
    public OrderCommandValidator() {
        d_methodMap = new HashMap<String, Method>();

        // We create a map that links a valid command to its validating method
        // (e.g. deploy is linked to validateDeployCommand)
        for (int l_i = 0; l_i < d_validCommands.size(); l_i++) {
            try {
                String l_orderType = d_validCommands.get(l_i);
                d_methodMap.put(
                        l_orderType,
                        OrderCommandValidator.class.getDeclaredMethod(
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
        List<String> l_commandArgs = p_command.getD_args();
        if ((l_commandType.equals("showmap") || l_commandType.equals("commit"))
                && l_commandArgs.isEmpty()) {
            return true;
        }
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
     * Validates the deploy command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateDeployCommand(Command p_command) {
        if (p_command.getD_args().size() != 2) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        try {
            int l_numArmies = Integer.parseInt(l_commandArgs.get(1));
        } catch (Exception l_e) {
            return false;
        }
        return true;
    }

    /**
     * Validates the advance command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateAdvanceCommand(Command p_command) {
        if (p_command.getD_args().size() != 3) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        try {
            int l_numArmies = Integer.parseInt(l_commandArgs.get(2));
        } catch (Exception l_e) {
            return false;
        }
        return true;
    }

    /**
     * Validates the bomb command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateBombCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * Validates the blockade command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateBlockadeCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * Validates the airlift command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateAirliftCommand(Command p_command) {
        if (p_command.getD_args().size() != 3) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        try {
            int l_numArmies = Integer.parseInt(l_commandArgs.get(2));
        } catch (Exception l_e) {
            return false;
        }
        return true;
    }

    /**
     * Validates the commit command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateCommitCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * Validates the negociate command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateNegotiateCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }


    /**
     *
     * @param p_command
     * @return
     */
    private boolean validateSavegameCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

}
