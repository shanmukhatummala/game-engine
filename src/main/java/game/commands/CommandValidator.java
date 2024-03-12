package game.commands;

public interface CommandValidator {

    abstract boolean validate(Command command) throws IllegalArgumentException;
}
