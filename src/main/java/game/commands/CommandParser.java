package game.commands;

import game.pojo.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandParser {
    private CommandValidator validator;
    private static Scanner scan = new Scanner(System.in);

    public CommandParser(CommandValidator validator) {
        this.validator = validator;
    }

    public boolean isCommandOption(String p_commandPart) {
        return (p_commandPart.charAt(0) == '-');
    }

    public List<Command> parse() {
        String[] l_commandParts = scan.nextLine().trim().split("\\s+");
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

    // old parsing method
    public Command parse(String inputCommand) throws IllegalArgumentException {
        String[] commandParts = inputCommand.trim().split("\\s+");
        String commandType = commandParts[0];
        List<String> commandArgs = Arrays.asList(commandParts).subList(1, commandParts.length);
        Command command = new Command(commandType, commandArgs);
        if (!validator.validate(command)) {
            throw new IllegalArgumentException("Invalid command for startup phase" + commandType);
        }
        return command;
    }

    public Command parse(Player p_player) {}
}
