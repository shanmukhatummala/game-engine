package game.logger;

import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class LogEntryWriter implements Observer {

    private final String d_fileName;

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
