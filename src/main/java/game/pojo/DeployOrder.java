package game.pojo;

/**
 * this class extends from order class and represent the deploy order type of orders
 */
public class DeployOrder extends Order{
    public DeployOrder(Country destination, int armyNumber) {
        super(destination, armyNumber);
    }


    @Override
    public void execute(){
        int l_currentArmyCount = this.getD_destination().getD_armyCount();
        this.getD_destination().setD_armyCount(l_currentArmyCount+this.getD_armyNumber());
    }
}
