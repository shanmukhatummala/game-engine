package game.order;

import game.pojo.Country;
import game.pojo.Player;

/** this class extends from order class and represent the deploy order type of orders */
public class Deploy extends Order {

    private Country d_destination;
    private int d_armyNumber;

    /**
     * The constructor of the class: calls the super constructor (parent constructor) class and
     * providing the parameters
     *
     * @param p_destination Country object of where the army will be deployed
     * @param p_armyNumber Integer of the army count
     */
    public Deploy(Country p_destination, Player p_initiator, int p_armyNumber) {
        super(p_initiator);
        this.d_destination = p_destination;
        this.d_armyNumber = p_armyNumber;
    }

    /**
     * Getter for the destination attribute
     *
     * @return the destination country to deploy reinforcements
     */
    public Country getD_destination() {
        return d_destination;
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
            int l_currentReinforcementsOfInitiator = this.getD_initiator().getD_reinforcements();
            int l_currentArmyCount = this.getD_destination().getD_armyCount();
            this.getD_destination().setD_armyCount(l_currentArmyCount + this.getD_armyNumber());
            this.getD_initiator()
                    .setD_reinforcements(
                            l_currentReinforcementsOfInitiator - this.getD_armyNumber());
        }
    }

    @Override
    public boolean valid() {
        if (!this.getD_initiator().getD_countries().contains(d_destination)) {
            System.out.println(d_destination + " is not owned by " + getD_initiator());
            return false;
        }

        if (this.getD_armyNumber() < 0
                || this.getD_armyNumber() > this.getD_initiator().getD_reinforcements()) {
            System.out.println("Invalid number of armies");
            return false;
        }

        return true;
    }
}