package game.pojo;

public class DeployOrder extends Order{
    public DeployOrder(Country destination, int armyNumber) {
        super(destination, armyNumber);
    }

    @Override
    public void execute(){
        int l_currentArmyCount = this.destination.getArmyCount();
        this.destination.setArmyCount(l_currentArmyCount+this.armyNumber);
    }
}
