package strategy;

import game.map.Map;
import game.pojo.Player;

public abstract class PlayerStrategy {
    public abstract  void deployStrongestCountry(Player player);
    public abstract void attackWithStrongestCountry(Player player, Map gameMap);
    public abstract void moveArmies(Player player, Map gameMap);

    public abstract void RandomDeploy(Player player);
    public abstract void RandomAttack(Player player, Map gameMap);
    public abstract void RandomMove(Player player, Map gameMap);

}
