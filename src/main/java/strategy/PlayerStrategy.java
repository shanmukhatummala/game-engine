package strategy;

import game.map.Map;
import game.pojo.Player;

public interface PlayerStrategy {
    public void deployStrongestCountry(Player player);
    public void attackWithStrongestCountry(Player player, Map gameMap);
    public void moveArmies(Player player, Map gameMap);

}
