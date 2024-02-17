package game.pojo;

public class DeployOrder extends Order{
    public DeployOrder(Country destination, int armyNumber) {
        super(destination, armyNumber);
    }

    @Override
    public void execute(){
        int l_currentArmyCount = this.destination.getD_armyCount();
        this.destination.setD_armyCount(l_currentArmyCount+this.armyNumber);
    }
}
