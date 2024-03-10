package game.util;

/** The ValidationHelper class provides utility methods for validating input data. */
public class ValidationHelper {

    /**
     * Checks if the given string represents a valid integer.
     *
     * @param s The string to be checked.
     * @return {@code true} if the string represents a valid integer, {@code false} otherwise.
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
