package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.util.List;
import java.util.Set;

abstract class StartUpPhase implements Phase {

    public static final String RESOURCES_PATH = "src/main/resources/";

    @Override
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map here";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> l_playersToAssignCard) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't execute orders";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't assign reinforcements";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't assign cards";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleDeployOrder() {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't deploy armies while in the start phase";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleAdvanceOrder() {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't advance armies while in the start phase";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleUseCardOrder() {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't use cards while in the start phase";
        printInvalidCommandMessage(l_message);
    }

    @Override
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        String l_message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Commit orders start phase";
        printInvalidCommandMessage(l_message);
    }
}
