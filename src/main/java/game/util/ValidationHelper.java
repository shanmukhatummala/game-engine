package game.util;

/** The ValidationHelper class provides utility methods for validating input data. */
public class ValidationHelper {

    /**
     * Checks if the given string represents a valid integer.
     *
     * @param p_s The string to be checked.
     * @return {@code true} if the string represents a valid integer, {@code false} otherwise.
     */
    public static boolean isInteger(String p_s) {
        try {
            Integer.parseInt(p_s);
        } catch (NumberFormatException | NullPointerException l_e) {
            return false;
        }
        return true;
    }
}
