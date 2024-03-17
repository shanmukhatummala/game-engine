package game.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to parse the inputs given by the user, create the commands and verify if they
 * are syntactically correct.
 */
public class CommandParser {

    /**
     * Verifies if the given string indicates an option (e.g. -add, -remove...)
     *
     * @param p_commandPart String to verify
     * @return true if the string is an option
     */
    private static boolean isCommandOption(String p_commandPart) {
        return (p_commandPart.charAt(0) == '-');
    }

    /**
     * Creates a list of commands based on the given string. Creates one command if there are no
     * option, or creates a command for each option.
     *
     * @param p_inputStr String from which commands can be created
     * @return List of commands corresponding to the given string
     */
    private static List<Command> createCommands(String p_inputStr) {
        String[] l_commandParts = p_inputStr.trim().split("\\s+");
        String l_commandType = l_commandParts[0];
        List<Command> l_commandList = new ArrayList<Command>();
        // if the String is just made up of one word, creates the command and return
        if (l_commandParts.length == 1) {
            Command l_command = new Command(l_commandType);
            l_commandList.add(l_command);
            return l_commandList;
        }

        // To parse the arguments :
        // if the first word is an option, do nothing
        // then, if another option is encountered, create a command with the words seen previously
        // and repeat the process
        // else, add word to list of arguments
        int l_i = 1;
        List<String> l_commandArgs = new ArrayList<String>();
        while (l_i < l_commandParts.length) {
            // if we encounter an option, create a command with the words that have been seen before
            if (isCommandOption(l_commandParts[l_i])) {
                if (l_commandArgs.size() > 0) {
                    Command l_command = new Command(l_commandType, l_commandArgs);
                    l_commandList.add(l_command);
                    l_commandArgs = new ArrayList<String>();
                }
            }
            l_commandArgs.add(l_commandParts[l_i]);
            l_i++;
        }

        Command l_command = new Command(l_commandType, l_commandArgs);
        l_commandList.add(l_command);

        return l_commandList;
    }

    /**
     * Parses the given input, creates the related commands and validates them. A command is
     * validated if it is syntactically correct.
     *
     * @param p_inputStr The input
     * @return List of validated commands corresponding to the given string
     * @throws IllegalArgumentException if the command is not written with the appropriate format
     *     (e.g. 'deploy 2 country' instead of 'deploy country 2')
     */
    public static List<Command> parse(String p_inputStr) throws IllegalArgumentException {
        CommandValidator l_editValidator = new EditCommandValidator();
        CommandValidator l_startUpValidator = new StartUpCommandValidator();
        CommandValidator l_orderValidator = new OrderCommandValidator();
        List<Command> l_commandList = createCommands(p_inputStr);
        for (Command l_command : l_commandList) {
            if (!l_editValidator.validate(l_command)
                    && !l_startUpValidator.validate(l_command)
                    && !l_orderValidator.validate(l_command)) {
                throw new IllegalArgumentException(
                        "Invalid command: " + l_command.getD_commandType() + l_command.getD_args());
            }
        }
        return l_commandList;
    }
}
