package game.commands;

import java.util.Arrays;
import java.util.List;

public class CommandParser {
    public Command parse(String inputCommand, String phase) throws IllegalArgumentException{
        String[] commandParts = inputCommand.trim().split("\\s+");
        String commandType = commandParts[0];
        List<String> commandArgs = Arrays.asList(commandParts).subList(1,commandParts.length);
        if(!isValidCommand(commandType, phase)){
            throw new IllegalArgumentException("Invalid command for"+phase+"phase"+commandType);
        }
        return new Command(commandType, commandArgs, phase);
    }



    private boolean isValidCommand(String commandType, String phase){
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
