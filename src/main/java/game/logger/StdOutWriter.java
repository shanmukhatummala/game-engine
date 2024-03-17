package game.logger;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StdOutWriter implements Observer {
    public void update(Observable p_observable) {
            List<String> l_logEntries = ((LogEntryBuffer) p_observable).getD_logEntries();

            l_logEntries.forEach(System.out::println);
    }
}
