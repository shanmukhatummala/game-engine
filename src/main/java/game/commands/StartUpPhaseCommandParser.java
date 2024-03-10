package game.commands;

import java.util.Arrays;
import java.util.List;

public class StartUpPhaseCommandParser implements CommandParser{
    @Override
    public Command parse(String inputCommand) throws IllegalArgumentException{
        String[] commandParts = inputCommand.trim().split("\\s+");
        String commandType = commandParts[0];
        List<String> commandArgs = Arrays.asList(commandParts).subList(1,commandParts.length);
        if(!isValidCommand(commandType)){
            throw new IllegalArgumentException("Invalid command for startup phase"+commandType);
        }
        return new Command(commandType, commandArgs);
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
