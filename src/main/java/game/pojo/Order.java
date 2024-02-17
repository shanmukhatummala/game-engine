package game.pojo;

/**
 * and abstract class to represent the type of order a player can give
 */
public abstract class Order {
    Country destination;
    int armyNumber;

    public Order(Country destination, int armyNumber) {
        this.destination = destination;
        this.armyNumber = armyNumber;
    }

    /**
     * this method is responsible for the behavior of the orders
     */
    public abstract void execute();

}
