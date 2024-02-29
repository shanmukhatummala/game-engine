package game.pojo;

/** and abstract class to represent the type of order a player can give */
public abstract class Order {
  private Country d_destination;
  private int d_armyNumber;

  /**
   * The constructor of Order class that initialize the attribute
   *
   * @param p_destination Country object of where the army will be deployed
   * @param p_armyNumber Integer of the army count
   */
  public Order(Country p_destination, int p_armyNumber) {
    this.d_destination = p_destination;
    this.d_armyNumber = p_armyNumber;
  }

  /**
   * Getter for the destination attribute
   *
   * @return Country object represent the destination of deployment
   */
  public Country getD_destination() {
    return d_destination;
  }

  /**
   * Getter for the armyNumber attribute
   *
   * @return Integer the army Count that will deploy
   */
  public int getD_armyNumber() {
    return d_armyNumber;
  }

  /** this method is responsible for the behavior of the orders */
  public abstract void execute();
}
