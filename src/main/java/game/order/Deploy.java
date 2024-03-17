package game.order;

import static game.map.MapHelper.getCountryByName;

import game.GameEngine;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

/** this class extends from order class and represent the deploy order type of orders */
public class Deploy extends Order {

    private String d_destinationCountryName;
    private int d_armyNumber;

    /**
     * The constructor of the class: calls the super constructor (parent constructor) class and
     * providing the parameters
     *
     * @param p_destinationCountryName Country name where the army will be deployed
     * @param p_armyNumber Integer of the army count
     */
    public Deploy(
            String p_destinationCountryName, Player p_initiator, int p_armyNumber, Map p_map) {
        super(p_initiator, p_map);
        this.d_destinationCountryName = p_destinationCountryName;
        this.d_armyNumber = p_armyNumber;
    }

    /**
     * Getter for the destination attribute
     *
     * @return the destination country to deploy reinforcements
     */
    public String getD_destinationCountryName() {
        return d_destinationCountryName;
    }

    /**
     * Getter for the armyNumber attribute
     *
     * @return the number of armies to deploy
     */
    public int getD_armyNumber() {
        return d_armyNumber;
    }

    /**
     * Overriding the execute method for the deploy order this method just add the number of army
     * deployed to the army count of the country
     */
    @Override
    public void execute() {

        if (valid()) {
            Country l_destination = getCountryByName(getD_map(), d_destinationCountryName);
            int l_currentArmyCount = l_destination.getD_armyCount();
            l_destination.setD_armyCount(l_currentArmyCount + this.getD_armyNumber());
            this.getD_initiator()
                    .setD_reinforcements(
                            getD_initiator().getD_reinforcements() - this.getD_armyNumber());
        }
    }

    @Override
    public boolean valid() {

        Country l_destination = getCountryByName(getD_map(), d_destinationCountryName);

        if (l_destination == null) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    d_destinationCountryName
                            + " doesn't exist in the map now. So, cannot deploy in this country.");
            return false;
        }

        if (!getD_initiator().getD_countries().contains(l_destination)) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    l_destination.getD_name() + " is not owned by " + getD_initiator().getD_name());
            return false;
        }

        if (getD_armyNumber() < 0 || getD_armyNumber() > getD_initiator().getD_reinforcements()) {
            GameEngine.LOG_ENTRY_BUFFER.addLogEntry(
                    "Invalid number of armies ("
                            + d_armyNumber
                            + ") to deploy on "
                            + d_destinationCountryName);
            return false;
        }

        return true;
    }
}
