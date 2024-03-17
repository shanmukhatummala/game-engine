package game.logger;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * This class is responsible for writing log entries to the standard output (stdout).
 * It implements the Observer interface to observe changes in a LogEntryBuffer and print new log entries to stdout.
 */
@RequiredArgsConstructor
public class StdOutWriter implements Observer {

    /**
     * Updates the writer with new log entries from the observable LogEntryBuffer.
     * Adds these log entries to the standard output
     *
     * @param p_observable The observable LogEntryBuffer that has been updated.
     */
    public void update(Observable p_observable) {
        List<String> l_logEntries = ((LogEntryBuffer) p_observable).getD_logEntries();

        l_logEntries.forEach(System.out::println);
    }
}
