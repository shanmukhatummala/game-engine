package game.states;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

import game.GameEngine;

public class EndGamePhase extends PlayPhase {

    public EndGamePhase() {
        GameEngine.d_logEntryBuffer.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }
}
