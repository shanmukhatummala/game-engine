package game.states;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;

/** Represents the state where the game has ended. */
public class EndGamePhase extends PlayPhase {

    /**
     * Constructs an EndGamePhase object. Adds a log entry to the global LOG_ENTRY_BUFFER indicating
     * the start of this phase.
     */
    public EndGamePhase() {
        GameEngine.LOG_ENTRY_BUFFER.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }
}
