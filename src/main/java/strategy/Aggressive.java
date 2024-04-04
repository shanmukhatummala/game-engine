package strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.*;

import static game.map.MapHelper.getCountryById;

public  class Aggressive extends PlayerStrategy {


    private static Aggressive aggressiveStrategy;

    public static Aggressive getAggressiveStrategy() {
        if (aggressiveStrategy == null) {
            aggressiveStrategy = new Aggressive() {};
        }
        return aggressiveStrategy;
    }

    /** Private constructor to make sure that the client calls getAggressiveStrategy() */
    private Aggressive() {}

    @Override
    public Command createOrder(Map map, Player player) {
        // Aggressive players don't need human input for creating orders, so this method is not implemented here
        return null;
    }
    @Override
        public void deployStrongestCountry(Player player) {
            Country strongestCountry = findStrongestCountry(player.getD_countries());
            int reinforcements = player.getD_reinforcements();
            strongestCountry.setD_armyCount(strongestCountry.getD_armyCount() + reinforcements);
            player.setD_reinforcements(0);
        }
    @Override
    public void attackWithStrongestCountry(Player player, Map gameMap) {
        Country strongestCountry = findStrongestCountry(player.getD_countries());
        Set<Integer> neighborIds = strongestCountry.getD_neighborIdList();

        for (Integer neighborId : neighborIds) {
            Country neighbor = getCountryById(gameMap, neighborId);
            if (neighbor != null && strongestCountry.getD_armyCount() > neighbor.getD_armyCount()) {
                int armiesToAttackWith = strongestCountry.getD_armyCount() - neighbor.getD_armyCount();
                neighbor.setD_armyCount(0); // Attack and conquer neighbor
                strongestCountry.setD_armyCount(armiesToAttackWith);
                break; // Stop after one successful attack
            }
        }
    }
    @Override

    public void moveArmies(Player player, Map gameMap) {
        Country strongestCountry = findStrongestCountry(player.getD_countries());
        List<Integer> neighbors = new ArrayList<>(strongestCountry.getD_neighborIdList());
        for (int neighborId : neighbors) {
            Country neighbor = getCountryById(gameMap, neighborId);
            if (neighbor != null && neighbor.getD_continent().equals(player)) {
                int totalArmies = strongestCountry.getD_armyCount() + neighbor.getD_armyCount();
                neighbor.setD_armyCount(totalArmies);
                strongestCountry.setD_armyCount(0);
                break; // Move armies to the first neighboring country in the same continent
            }
        }
    }

    @Override
    public void RandomDeploy(Player player) {
    }

    @Override
    public void RandomAttack(Player player, Map gameMap) {

    }

    @Override
    public void RandomMove(Player player, Map gameMap) {

    }

    private Country findStrongestCountry(List<Country> countries) {
            return Collections.max(countries, Comparator.comparingInt(Country::getD_armyCount));
        }
}



