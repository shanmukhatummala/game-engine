package game.order;

import static game.map.MapHelper.getCountryByName;
import static game.map.MapHelper.getCountryOwner;
import static game.map.MapHelper.isAdjacent;
import static game.pojo.Player.Card.BOMB;

import game.GameEngine;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

/** This class is used for performing the Bomb operation */
public class Bomb extends Order {

    private String d_targetCountryName;

    /**
     * Constructor for Bomb class
     *
     * @param p_targetCountryName target country to bomb
     * @param p_initiator player who initiated the order
     * @param p_map The map where the game is being played
     */
    public Bomb(String p_targetCountryName, Player p_initiator, Map p_map) {
        super(p_initiator, p_map);
        this.d_targetCountryName = p_targetCountryName;
    }

    /**
     * Getter for the target country to bomb
     *
     * @return target country which will be bombed
     */
    public String getD_targetCountryName() {
        return d_targetCountryName;
    }

    /**
     * Destroys half of the armies located on an opponent’s territory that is adjacent to one of the
     * current player’s territories
     */
    @Override
    public void execute() {

        if (valid()) {

            Country l_target = getCountryByName(getD_map(), d_targetCountryName);
            Player l_targetOwner = getCountryOwner(l_target, getD_map().getD_players());

            if (l_targetOwner != null
                    && (getD_initiator()
                                    .getD_negotiatedPlayers()
                                    .contains(l_targetOwner.getD_name())
                            || l_targetOwner
                                    .getD_negotiatedPlayers()
                                    .contains(getD_initiator().getD_name()))) {
                GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                        "Both players, "
                                + l_targetOwner.getD_name()
                                + " and "
                                + getD_initiator().getD_name()
                                + ", are under negotiation. So, cannot attack.");
                return;
            }

            int l_armyCountAfterBombing = l_target.getD_armyCount() / 2;
            l_target.setD_armyCount(l_armyCountAfterBombing);
            getD_initiator().getD_cards().remove(BOMB);
        }
    }

    @Override
    public boolean valid() {

        Country l_target = getCountryByName(getD_map(), d_targetCountryName);

        if (l_target == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_targetCountryName
                            + " doesn't exist in the map now. So, cannot bomb this country.");
            return false;
        }

        List<Country> l_countriesOfInitiator = getD_initiator().getD_countries();

        if (!getD_initiator().getD_cards().contains(BOMB)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    getD_initiator().getD_name()
                            + " doesn't have a BOMB card. So, cannot execute bomb order.");
            return false;
        }

        if (l_countriesOfInitiator.contains(l_target)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    "Target country, "
                            + l_target.getD_name()
                            + ", belongs to the initiator, "
                            + getD_initiator().getD_name()
                            + ". So, cannot bomb your own country.");
            return false;
        }

        if (!isAdjacent(l_countriesOfInitiator, l_target)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    "Target country, "
                            + l_target.getD_name()
                            + ", is not adjacent to the player. So, cannot bomb this country.");
            return false;
        }

        return true;
    }
}
