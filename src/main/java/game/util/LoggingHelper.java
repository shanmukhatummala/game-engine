package game.util;

public class LoggingHelper {

    public static String getLoggerEntryForPhaseChange(Class p_class) {
        return new StringBuilder()
                .append("<< PHASE: ")
                .append(p_class.getSimpleName())
                .append(" >>")
                .toString();
    }
}
