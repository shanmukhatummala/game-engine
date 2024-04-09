package game.order;

import static game.map.MapHelper.getCountryByName;
import static game.pojo.Player.Card.BLOCKADE;

import game.GameEngine;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

/** Class representing a blockade order */
public class Blockade extends Order {

    /** Represents the name of the target country. */
    private String d_targetName;

    /**
     * Constructor for the Blockade order
     *
     * @param p_targetName The name of the country to be blockaded
     * @param p_initiator player who initiated the order
     * @param p_map The map where the game is being played
     */
    public Blockade(String p_targetName, Player p_initiator, Map p_map) {
        super(p_initiator, p_map); // Call the superclass constructor with null destination
        this.d_targetName = p_targetName;
    }

    /**
     * Getter for the target country to blockade
     *
     * @return target country which will be blockaded
     */
    public String getD_targetName() {
        return d_targetName;
    }

    /**
     * Executes the blockade order by increasing the armies in the target country by 3 times and
     * making it as a neutral country
     */
    public void execute() {
        if (valid()) {
            Country l_target = getCountryByName(getD_map(), d_targetName);
            int l_armyCountAfterBlockade = l_target.getD_armyCount() * 3;
            l_target.setD_armyCount(l_armyCountAfterBlockade);
            this.getD_initiator().getD_countries().remove(l_target);
            getD_initiator().getD_cards().remove(BLOCKADE);
        }
    }

    /** Checks the validity of the Blockade command */
    @Override
    public boolean valid() {

        Country l_target = getCountryByName(getD_map(), d_targetName);

        if (l_target == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_targetName
                            + " doesn't exist in the map now. So, cannot blockade this country.");
            return false;
        }

        if (!getD_initiator().getD_cards().contains(BLOCKADE)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    getD_initiator().getD_name()
                            + " doesn't have a BLOCKADE card. So, cannot execute blockade order.");
            return false;
        }
        if (!this.getD_initiator().getD_countries().contains(l_target)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    "Target country, "
                            + l_target.getD_name()
                            + ", does not belong to the initiator, "
                            + getD_initiator().getD_name()
                            + ". So, cannot blockade the country.");
            return false;
        }
        return true;
    }
}
