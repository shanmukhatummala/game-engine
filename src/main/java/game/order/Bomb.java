package game.order;

import static game.map.MapHelper.isAdjacent;
import static game.pojo.Player.Card.BOMB;

import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

/** This class is used for performing the Bomb operation */
public class Bomb extends Order {

    private Country d_target;

    /**
     * Constructor for Bomb class
     *
     * @param p_target target country to bomb
     * @param p_initiator player who initiated the order
     */
    public Bomb(Country p_target, Player p_initiator) {
        super(p_initiator);
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
     * Destroys half of the armies located on an opponent’s territory that is adjacent to one of the
     * current player’s territories
     */
    @Override
    public void execute() {

        if (valid()) {
            int l_armyCountAfterBombing = d_target.getD_armyCount() / 2;
            d_target.setD_armyCount(l_armyCountAfterBombing);
            getD_initiator().getD_cards().remove(BOMB);
        }
    }

    @Override
    public boolean valid() {

        List<Country> l_countriesOfInitiator = getD_initiator().getD_countries();

        if (!getD_initiator().getD_cards().contains(BOMB)) {
            System.out.println("You don't have a BOMB card. So, cannot execute bomb order.");
            return false;
        }

        if (l_countriesOfInitiator.contains(d_target)) {
            System.out.println(
                    "Target country belong to the initiator. So, cannot bomb your own country.");
            return false;
        }

        if (!isAdjacent(l_countriesOfInitiator, d_target)) {
            System.out.println(
                    "Target country is not adjacent to the player. So, cannot bomb this country. Try another: ");
            return false;
        }

        return true;
    }
}
