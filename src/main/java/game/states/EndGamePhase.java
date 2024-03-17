package game.states;

import game.GameEngine;

import java.util.List;

import static game.util.LoggingHelper.getLoggerEntryForPhaseChange;

public class EndGamePhase extends PlayPhase {

    public EndGamePhase() {
        GameEngine.d_logEntryBuffer.addLogEntry(getLoggerEntryForPhaseChange(this.getClass()));
    }
}
