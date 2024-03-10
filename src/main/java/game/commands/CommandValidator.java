package game.commands;

public interface CommandValidator {
    boolean validate(Command command) throws IllegalArgumentException;
}
