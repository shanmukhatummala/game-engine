package game.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for verifying if a given command is syntactically correct. */
public class CommandValidator {

    static List<String> d_noArgumentCommands =
            Arrays.asList("showmap", "validatemap", "commit", "assigncountries");

    static List<String> d_oneArgumentCommands =
            Arrays.asList(
                    "savemap",
                    "bomb",
                    "blockade",
                    "negotiate",
                    "savegame",
                    "loadmap",
                    "editmap",
                    "loadgame");

    static List<String> d_otherCommands =
            Arrays.asList(
                    "gameplayer",
                    "deploy",
                    "advance",
                    "airlift",
                    "savefiletype",
                    "editcontinent",
                    "editcountry",
                    "editneighbor",
                    "tournament");

    Map<String, Method> d_methodMap;

    /** Constructor for the Validator where the d_methodMap attribute is built */
    public CommandValidator() {

        d_methodMap = new HashMap<String, Method>();
        String l_orderType = null;

        // Links the commands that require no arguments to the validateNoArgCommand method
        for (int l_i = 0; l_i < d_noArgumentCommands.size(); l_i++) {
            try {
                l_orderType = d_noArgumentCommands.get(l_i);
                d_methodMap.put(
                        l_orderType,
                        CommandValidator.class.getDeclaredMethod(
                                "validateNoArgCommand", Command.class));
            } catch (NoSuchMethodException l_e) {
                l_e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        // Links the commands that require only one argument to the validateOneArgCommand method
        for (int l_i = 0; l_i < d_oneArgumentCommands.size(); l_i++) {
            try {
                l_orderType = d_oneArgumentCommands.get(l_i);
                d_methodMap.put(
                        l_orderType,
                        CommandValidator.class.getDeclaredMethod(
                                "validateOneArgCommand", Command.class));
            } catch (NoSuchMethodException l_e) {
                l_e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        // We create a map that links a valid command to its validating method
        // (e.g. tournament is linked to validateTournamentCommand)
        for (int l_i = 0; l_i < d_otherCommands.size(); l_i++) {
            try {
                l_orderType = d_otherCommands.get(l_i);
                d_methodMap.put(
                        l_orderType,
                        CommandValidator.class.getDeclaredMethod(
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

    /**
     * Validates the given command
     *
     * @param p_command Command to validate
     * @return true if the command is syntactically correct
     */
    public boolean validate(Command p_command) {
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

    private boolean validateNoArgCommand(Command p_command) {
        return (p_command.getD_args().size() == 0);
    }

    private boolean validateOneArgCommand(Command p_command) {
        return (p_command.getD_args().size() == 1);
    }

    /**
     * Validates the gameplayer command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateGameplayerCommand(Command p_command) {
        if (p_command.getD_args().size() != 2 && p_command.getD_args().size() != 3) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        return (l_commandArgs.get(0).equals("-add") || l_commandArgs.get(0).equals("-remove"));
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
     * Validates the savefiletype command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateSavefiletypeCommand(Command p_command) {
        return (p_command.getD_args().size() == 1
                && (p_command.getD_args().get(0).equals("1")
                        || p_command.getD_args().get(0).equals("2")));
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
     * Validates the tournament command
     *
     * @param p_command given command
     * @return true if command is valid
     */
    private boolean validateTournamentCommand(Command p_command) {
        if (p_command.getD_args().size() < 2) {
            return false;
        }
        List<String> l_commandArgs = p_command.getD_args();

        return (l_commandArgs.get(0).equals("-M")
                || l_commandArgs.get(0).equals("-P")
                || l_commandArgs.get(0).equals("-G")
                || l_commandArgs.get(0).equals("-D"));
    }
}
