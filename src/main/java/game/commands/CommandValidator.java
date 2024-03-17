package game.commands;

/** Responsible for verifying if a given command is syntactically correct. */
public interface CommandValidator {
    /**
     * Validates the given command
     *
     * @param command Command to validate
     * @return true if the command is syntactically correct
     * @throws IllegalArgumentException if the command is not correct
     */
    abstract boolean validate(Command p_command) throws IllegalArgumentException;
}
