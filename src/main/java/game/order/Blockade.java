package game.order;

import static game.pojo.Player.Card.BLOCKADE;

import game.pojo.Country;
import game.pojo.Player;

/** Class representing a blockade order */
public class Blockade extends Order {

    private Country d_target;

    /**
     * Constructor for the Blockade order
     *
     * @param p_target The name of the country to be blockaded
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
     * Executes the blockade order by increasing the armies in the target country by 3 times and
     * making it as a neutral country
     */
    public void execute() {
        if (valid()) {
            int l_armyCountAfterBlockade = d_target.getD_armyCount() * 3;
            d_target.setD_armyCount(l_armyCountAfterBlockade);
            this.getD_initiator().getD_countries().remove(d_target);
            getD_initiator().getD_cards().remove(BLOCKADE);
        }
    }

    /** Checks the validity of the Blockade command */
    @Override
    public boolean valid() {
        if (!getD_initiator().getD_cards().contains(BLOCKADE)) {
            System.out.println(
                    "You don't have a BLOCKADE card. So, cannot execute blockade order.");
            return false;
        }
        if (!this.getD_initiator().getD_countries().contains(d_target)) {
            System.out.println(
                    "Target country does not belong to the initiator. So, cannot blockade the country.");
            return false;
        }
        return true;
    }
}
