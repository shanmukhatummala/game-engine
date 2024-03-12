package game.commands;

import java.util.Arrays;
import java.util.List;

public class StartUpCommandValidator implements CommandValidator {
    List<String> validMultiArgsCommand;
    List<String> validSingleWordCommand;

    public StartUpCommandValidator() {
        this.validMultiArgsCommand =
                Arrays.asList(
                        "editcontinent",
                        "editcountry",
                        "editneighbor",
                        "editmap",
                        "gameplayer",
                        "loadmap");
        validSingleWordCommand = Arrays.asList("showmap", "savemap", "validatemap", "loadmap");
    }

    @Override
    public boolean validate(Command command) throws IllegalArgumentException {
        String commandType = command.getCommandType();
        if (validSingleWordCommand.contains(command.getCommandType())
                && command.getArgs().isEmpty()) {
            return true;
        } else if (validMultiArgsCommand.contains(command.getCommandType())) {
            if (commandType.equals(validMultiArgsCommand.get(0))) {
                return validateEditContinentCommand(command);
            } else if (commandType.equals(validMultiArgsCommand.get(1))) {
                return validateEditCountryCommand(command);
            } else if (commandType.equals(validMultiArgsCommand.get(2))) {
                return validateEditNeighborCommand(command);
            } else if (commandType.equals(validMultiArgsCommand.get(3))) {
                return validateEditMapCommand(command);
            } else if (commandType.equals(validMultiArgsCommand.get(4))) {
                return validateGamePlayerCommand(command);
            } else {
                return validateLoadMapCommand(command);
            }
        } else {
            return false;
        }
    }

    private boolean validateEditCountryCommand(Command command) {
        // editCountry command validation
        return true;
    }

    private boolean validateEditContinentCommand(Command command) {
        // editContinent command validation
        return true;
    }

    private boolean validateEditNeighborCommand(Command command) {
        // editNeighbor command validation
        return true;
    }

    private boolean validateEditMapCommand(Command command) {
        // editMap command validation
        return true;
    }

    private boolean validateGamePlayerCommand(Command command) {
        // game player command validation
        return true;
    }

    private boolean validateLoadMapCommand(Command command) {
        // load map command validation
        return true;
    }
}
