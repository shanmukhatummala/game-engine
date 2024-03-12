package game.commands;

import game.GameEngine;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    private boolean isCommandOption(String p_commandPart) {
        return (p_commandPart.charAt(0) == '-');
    }

    private List<Command> parse(String p_inputStr) {
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

    public Command parse(Player p_player, String p_inputStr) {
        CommandValidator l_orderValidator = new OrderCommandValidator(p_player);
        List<Command> l_commandList = parse(p_inputStr);
        if (l_commandList.size() > 1)
            throw new IllegalArgumentException("Can't have more than one command for Player");
        Command l_command = l_commandList.get(0);
        if (!l_orderValidator.validate(l_command)) {
            throw new IllegalArgumentException(
                    "Invalid command for issuing orders: "
                            + l_command.getCommandType()
                            + l_command.getArgs());
        }
        return l_command;
    }

    public List<Command> parse(GameEngine p_engine, String p_inputStr) {
        CommandValidator l_orderValidator = new StartUpCommandValidator(p_engine);
        List<Command> l_commandList = parse(p_inputStr);
        for (Command l_command : l_commandList) {
            if (!l_orderValidator.validate(l_command)) {
                throw new IllegalArgumentException(
                        "Invalid command for the startup phase: "
                                + l_command.getCommandType()
                                + l_command.getArgs());
            }
        }
        return l_commandList;
    }
}
