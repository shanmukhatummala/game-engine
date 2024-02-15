package game.pojo;

public abstract class Order {
    Country destination;
    int armyNumber;

    public Order(Country destination, int armyNumber) {
        this.destination = destination;
        this.armyNumber = armyNumber;
    }

}
