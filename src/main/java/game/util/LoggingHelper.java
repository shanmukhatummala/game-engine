package game.util;

/** This class provides helper methods for logging. */
public class LoggingHelper {

    /**
     * Generates a logger entry for a phase change.
     *
     * @param p_class The class representing the phase change.
     * @return A string representing the logger entry for the phase change.
     */
    public static String getLoggerEntryForPhaseChange(Class p_class) {
        return new StringBuilder()
                .append("<< PHASE: ")
                .append(p_class.getSimpleName())
                .append(" >>")
                .toString();
    }
}
