package strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;
import java.util.List;
import java.util.Random;

import static game.map.MapHelper.getCountryById;

public  class Random_strategie extends PlayerStrategy {

    private static Random_strategie RandomStrategy;

    public static Random_strategie getAggressiveStrategy() {
        if (RandomStrategy == null) {
            RandomStrategy = new Random_strategie() {};
        }
        return RandomStrategy;
    }

    /** Private constructor to make sure that the client calls getAggressiveStrategy() */
    private Random_strategie() {}
    @Override
    public Command createOrder(Map p_map, Player p_player) {
        return null;
    }

    @Override
    public void deployStrongestCountry(Player player) {

    }

    @Override
    public void attackWithStrongestCountry(Player player, Map gameMap) {

    }

    @Override
    public void moveArmies(Player player, Map gameMap) {

    }

    @Override
    public void RandomDeploy(Player player) {

        List<Country> countries = player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            int reinforcements = player.getD_reinforcements();
            randomCountry.setD_armyCount(randomCountry.getD_armyCount() + reinforcements);
            player.setD_reinforcements(0);
            System.out.println("Deployed " + reinforcements + " armies to " + randomCountry.getD_name());
        }
    }
    @Override
    public void RandomAttack(Player player, Map gameMap) {
        List<Country> countries = player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            List<Integer> neighborIds = (List<Integer>) randomCountry.getD_neighborIdList();
            if (!neighborIds.isEmpty()) {
                int neighborIndex = random.nextInt(neighborIds.size());
                int neighborId = neighborIds.get(neighborIndex);
                Country neighbor = getCountryById(gameMap, neighborId);
                if (neighbor != null && randomCountry.getD_armyCount() > neighbor.getD_armyCount()) {
                    int armiesToAttackWith = randomCountry.getD_armyCount() - neighbor.getD_armyCount();
                    neighbor.setD_armyCount(0); // Attack and conquer neighbor
                    randomCountry.setD_armyCount(armiesToAttackWith);
                    System.out.println(randomCountry.getD_name() + " attacks " + neighbor.getD_name() +
                            " with " + armiesToAttackWith + " armies");
                }
            }
        }
    }

    @Override
    public void RandomMove(Player player, Map gameMap) {
        List<Country> countries = player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            List<Integer> neighborIds = (List<Integer>) randomCountry.getD_neighborIdList();
            if (!neighborIds.isEmpty()) {
                int neighborIndex = random.nextInt(neighborIds.size());
                int neighborId = neighborIds.get(neighborIndex);
                Country neighbor = getCountryById(gameMap, neighborId);
                if (neighbor != null && neighbor.getD_continent().equals(player)) {
                    int totalArmies = randomCountry.getD_armyCount() + neighbor.getD_armyCount();
                    neighbor.setD_armyCount(totalArmies);
                    randomCountry.setD_armyCount(0);
                    System.out.println("Moved armies from " + randomCountry.getD_name() + " to " + neighbor.getD_name());
                }
            }
        }
    }

}
