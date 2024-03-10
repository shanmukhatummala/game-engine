package game.commands;

import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private CommandValidator validator;
    public CommandParser(CommandValidator validator){
        this.validator = validator;
    }
    public Command parse(String inputCommand) throws IllegalArgumentException{
        String[] commandParts = inputCommand.trim().split("\\s+");
        String commandType = commandParts[0];
        List<String> commandArgs = Arrays.asList(commandParts).subList(1,commandParts.length);
        Command command =  new Command(commandType, commandArgs);
        if(!validator.validate(command)){
            throw new IllegalArgumentException("Invalid command for startup phase"+commandType);
        }
        return command;
    }

    private boolean isValidCommand(String commandType){
        String phase = "startup";
        return switch (phase) {
            case "startup" -> isValidStartupCommand(commandType);
            case "order" -> isValidOrderCommand(commandType);
            default -> false;
        };
    }


    private boolean isValidStartupCommand(String commandType) {
        List<String> validCommands = Arrays.asList(
                "editcontinent", "editcountry", "editneighbor", "showmap",
                "savemap", "editmap", "validatemap", "gameplayer", "loadmap");
        return validCommands.contains(commandType);
    }

    private boolean isValidOrderCommand(String commandType) {
        List<String> validCommands = Arrays.asList(
                "deploy", "showmap", "advance", "bomb", "blockade");
        return validCommands.contains(commandType);
    }
}
