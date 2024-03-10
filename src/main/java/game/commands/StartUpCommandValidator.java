package game.commands;

public class StartUpCommandValidator implements CommandValidator{
    @Override
    public boolean validate(Command command) throws IllegalArgumentException{
        return true;
    }

}
