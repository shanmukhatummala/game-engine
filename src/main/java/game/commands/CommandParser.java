package game.commands;

public interface CommandParser {
    Command parse(String input)throws IllegalArgumentException;
}
