package game.order;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

/** Class representing a blockade order */
public class Blockade extends Order {
    private Country d_target;

    /**
     * Constructor for the Blockade order
     *
     * @param p_target   The name of the country to be blockaded
     * @param p_initiator player who initiated the order
     */
    public Blockade(Country p_target, Player p_initiator) {
        super(p_initiator); // Call the superclass constructor with null destination
        this.d_target = p_target;
    }

    /**
     * Getter for the target country to bomb
     *
     * @return target country which will be bombed
     */
    public Country getD_target() {
        return d_target;
    }
    /**
     * Executes the blockade order
     */
    public void execute() {

        List<Country> l_countriesOfInitiator = this.getD_initiator().getD_countries();

        if (!getD_initiator().hasBlockadeCard()) {
            System.out.println("Player does not have a blockade card. Cannot perform the operation");
            return;
        }

        if (!l_countriesOfInitiator.contains(d_target)) {
            System.out.println("Target country does not belong to the initiator. So, cannot blockade the country.");
            return;
        }

        int l_armyCountAfterBlockade = d_target.getD_armyCount() * 3;
        d_target.setD_armyCount(l_armyCountAfterBlockade);
        l_countriesOfInitiator.remove(d_target);

    }

}
