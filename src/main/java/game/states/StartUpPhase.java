package game.states;

import game.map.Map;

import java.util.*;

abstract class StartUpPhase implements Phase {

    public static final String RESOURCES_PATH = "src/main/resources/";


    @Override
    public void handleDeployOrder() {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't deploy armies while in the start phase";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleAdvanceOrder() {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't advance armies while in the start phase";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleUseCardOrder() {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't use cards while in the start phase";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleCommit() {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Commit orders start phase";
        printInvalidCommandMessage(message);
    }
}
