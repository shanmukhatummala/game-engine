package game.pojo;

/**
 * and abstract class to represent the type of order a player can give
 */
public abstract class Order {
    private Country d_destination;
    private int d_armyNumber;

    public Order(Country p_destination, int p_armyNumber) {
        this.d_destination = p_destination;
        this.d_armyNumber = p_armyNumber;
    }

    public Country getD_destination() {
        return d_destination;
    }

    public int getD_armyNumber() {
        return d_armyNumber;
    }
    /**
     * this method is responsible for the behavior of the orders
     */
    public abstract void execute();

}
