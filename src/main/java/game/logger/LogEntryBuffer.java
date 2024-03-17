package game.logger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class LogEntryBuffer extends Observable {

    private final List<String> d_logEntries;

    public void addLogEntries(List<String> p_newLogEntries) {
        d_logEntries.addAll(p_newLogEntries);
        notifyObservers(this);
        this.clearLogEntries();
    }

    public void clearLogEntries() {
        d_logEntries.clear();
    }
}
