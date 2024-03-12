package game.commands;

import java.util.Arrays;
import java.util.List;

public class OrderCommandValidator implements CommandValidator {
    List<String> validCommands =
            Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");

    @Override
    public boolean validate(Command command) throws IllegalArgumentException {
        String commandType = command.getCommandType();
        if (command.getCommandType().equals("showmap") && command.getArgs().isEmpty()) {
            return true;
        } else if (validCommands.contains(command.getCommandType())) {
            if (commandType.equals(validCommands.get(0))) {
                return validateDeployOrder(command);
            } else if (commandType.equals(validCommands.get(1))) {
                return validateAdvanceOrder(command);
            } else if (commandType.equals(validCommands.get(2))) {
                return validateBombOrder(command);
            } else if (commandType.equals(validCommands.get(3))) {
                return validateBlockadeOrder(command);
            } else if (commandType.equals(validCommands.get(4))) {
                return validateAirliftOrder(command);
            } else {
                return validateNegotiateOrder(command);
            }
        } else {
            return false;
        }
    }

    private boolean validateDeployOrder(Command command) {
        // editCountry command validation
        return true;
    }

    private boolean validateAdvanceOrder(Command command) {
        // editContinent command validation
        return true;
    }

    private boolean validateBombOrder(Command command) {
        // editNeighbor command validation
        return true;
    }

    private boolean validateBlockadeOrder(Command command) {
        // editMap command validation
        return true;
    }

    private boolean validateAirliftOrder(Command command) {
        // game player command validation
        return true;
    }

    private boolean validateNegotiateOrder(Command command) {
        // load map command validation
        return true;
    }
}
