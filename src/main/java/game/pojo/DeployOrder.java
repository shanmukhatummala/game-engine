package game.pojo;

/** this class extends from order class and represent the deploy order type of orders */
public class DeployOrder extends Order {
  /**
   * The constructor of the class: calls the super constructor (parent constructor) class and
   * providing the parameters
   *
   * @param p_destination Country object of where the army will be deployed
   * @param p_armyNumber Integer of the army count
   */
  public DeployOrder(Country p_destination, int p_armyNumber) {
    super(p_destination, p_armyNumber);
  }

  /**
   * Overriding the execute method for the deploy order this method just add the number of army
   * deployed to the army count of the country
   */
  @Override
  public void execute() {
    int l_currentArmyCount = this.getD_destination().getD_armyCount();
    this.getD_destination().setD_armyCount(l_currentArmyCount + this.getD_armyNumber());
  }
}
