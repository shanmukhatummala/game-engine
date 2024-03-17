package game.logger;

import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for writing log entries to a file. It implements the Observer interface to observe changes
 * in a LogEntryBuffer and write new log entries to the specified file.
 */
@RequiredArgsConstructor
public class LogEntryWriter implements Observer {

    /**
     * The name of the file to which log entries will be written.
     */
    private final String d_fileName;

    /**
     * Updates the writer with new log entries from the observable LogEntryBuffer.
     *
     * @param p_observable The observable LogEntryBuffer that has been updated.
     */
    public void update(Observable p_observable) {
        try {
            FileWriter l_fileWriter = new FileWriter(d_fileName, true);

            List<String> l_logEntries = ((LogEntryBuffer) p_observable).getD_logEntries();

            l_logEntries.forEach(
                    l_logEntry -> {
                        try {
                            l_fileWriter.write(l_logEntry);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            l_fileWriter.close();
        } catch (IOException ioe) {
            System.out.println("Warning: IOException in writing to log file");
        }
    }
}
