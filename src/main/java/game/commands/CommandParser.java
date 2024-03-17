package game.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    private static boolean isCommandOption(String p_commandPart) {
        return (p_commandPart.charAt(0) == '-');
    }

    private static List<Command> createCommands(String p_inputStr) {
        String[] l_commandParts = p_inputStr.trim().split("\\s+");
        String l_commandType = l_commandParts[0];
        List<Command> l_commandList = new ArrayList<Command>();
        if (l_commandParts.length == 1) {
            Command l_command = new Command(l_commandType);
            l_commandList.add(l_command);
            return l_commandList;
        }

        int l_i = 1;
        List<String> l_commandArgs = new ArrayList<String>();
        while (l_i < l_commandParts.length) {
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
