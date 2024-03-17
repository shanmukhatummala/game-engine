package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.util.List;
import java.util.Set;

abstract class StartUpPhase implements Phase {


    @Override
    public void handleIssuingOrders(Map p_map, Player p_player, Command p_command) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Validate a map here";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleExecutingOrders(
            Map p_map, GameEngine p_ge, Set<Player> l_playersToAssignCard) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't execute orders";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleReinforcementsAssignment(Map p_map, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't assign reinforcements";
        printInvalidCommandMessage(message);
    }

    @Override
    public void handleCardAssignment(Set<Player> p_players, GameEngine p_ge) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't assign cards";
        printInvalidCommandMessage(message);
    }

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
    public void handleCommit(List<Player> p_playersLeftToIssueOrder, Player p_currentPlayer) {
        String message =
                "Invalid Command in state"
                        + this.getClass().getSimpleName()
                        + " you can't Commit orders start phase";
        printInvalidCommandMessage(message);
    }
}
