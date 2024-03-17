package game.logger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * This class represents a buffer for log entries. It extends the Observable class to support observer pattern.
 * LogEntryBuffer maintains a list of log entries and notifies its observers when new log entries are added.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class LogEntryBuffer extends Observable {

    /**
     * The list of log entries stored in this buffer.
     */
    private final List<String> d_logEntries;

    /**
     * Adds a new log entry to the buffer and notifies observers.
     *
     * @param p_newLogEntry The new log entry to be added.
     */
    public void addLogEntry(String p_newLogEntry) {
        d_logEntries.add(p_newLogEntry);
        notifyObservers(this);
        this.clearLogEntries();
    }

    /**
     * Adds new log entries to the buffer and notifies observers.
     *
     * @param p_newLogEntries The list of new log entries to be added.
     */
    public void addLogEntries(List<String> p_newLogEntries) {
        d_logEntries.addAll(p_newLogEntries);
        notifyObservers(this);
        this.clearLogEntries();
    }

    /**
     * Clears all log entries from the buffer.
     */
    public void clearLogEntries() {
        d_logEntries.clear();
    }
}
