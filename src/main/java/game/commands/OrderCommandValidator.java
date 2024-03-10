package game.commands;

import java.util.Arrays;
import java.util.List;

public class OrderCommandValidator implements CommandValidator{
    @Override
    public boolean validate(Command command) throws IllegalArgumentException{
        return true;
    }
    private boolean isValidOrderCommand(String commandType) {
        List<String> validCommands = Arrays.asList(
                "deploy", "showmap", "advance", "bomb", "blockade");
        return validCommands.contains(commandType);
    }
}
