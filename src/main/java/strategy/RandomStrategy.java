package strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;
import java.util.Random;

import static game.map.MapHelper.getCountryById;

public class RandomStrategy extends PlayerStrategy {

    private static RandomStrategy randomStrategy;

    public static RandomStrategy getRandomStrategy() {
        if (randomStrategy == null) {
            randomStrategy = new RandomStrategy();
        }
        return randomStrategy;
    }

    /** Private constructor to make sure that the client calls getRandomStrategy() */
    private RandomStrategy() {}

    @Override
    public Command createOrder(Map p_map, Player p_player) {
        Random random = new Random();
        int action = random.nextInt(3); // 0 for deploy, 1 for attack, 2 for move

        switch (action) {
            case 0:
                return deployRandomly(p_player);
            case 1:
                return attackRandomly(p_map, p_player);
            case 2:
                return moveRandomly(p_map, p_player);
            default:
                return new Command("end_turn");
        }
    }

    private Command deployRandomly(Player p_player) {
        List<Country> countries = p_player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            int reinforcements = p_player.getD_reinforcements();
            randomCountry.setD_armyCount(randomCountry.getD_armyCount() + reinforcements);
            p_player.setD_reinforcements(0);
            System.out.println("Deployed " + reinforcements + " armies to " + randomCountry.getD_name());
            return new Command("deploy");
        }
        return createOrder(null, p_player); // No valid country to deploy, try again
    }

    private Command attackRandomly(Map p_map, Player p_player) {
        List<Country> countries = p_player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            List<Integer> neighborIds = (List<Integer>) randomCountry.getD_neighborIdList();
            if (!neighborIds.isEmpty()) {
                int neighborIndex = random.nextInt(neighborIds.size());
                int neighborId = neighborIds.get(neighborIndex);
                Country neighbor = getCountryById(p_map, neighborId);
                if (neighbor != null && randomCountry.getD_armyCount() > neighbor.getD_armyCount()) {
                    int armiesToAttackWith = randomCountry.getD_armyCount() - neighbor.getD_armyCount();
                    neighbor.setD_armyCount(0); // Attack and conquer neighbor
                    randomCountry.setD_armyCount(armiesToAttackWith);
                    System.out.println(randomCountry.getD_name() + " attacks " + neighbor.getD_name() +
                            " with " + armiesToAttackWith + " armies");
                    return new Command("attack");
                }
            }
        }
        return createOrder(p_map, p_player); // No valid attack, try again
    }

    private Command moveRandomly(Map p_map, Player p_player) {
        List<Country> countries = p_player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            List<Integer> neighborIds = (List<Integer>) randomCountry.getD_neighborIdList();
            if (!neighborIds.isEmpty()) {
                int neighborIndex = random.nextInt(neighborIds.size());
                int neighborId = neighborIds.get(neighborIndex);
                Country neighbor = getCountryById(p_map, neighborId);
                if (neighbor != null && neighbor.getD_continent().equals(p_player)) {
                    int totalArmies = randomCountry.getD_armyCount() + neighbor.getD_armyCount();
                    neighbor.setD_armyCount(totalArmies);
                    randomCountry.setD_armyCount(0);
                    System.out.println("Moved armies from " + randomCountry.getD_name() + " to " + neighbor.getD_name());
                    return new Command("move");
                }
            }
        }
        return createOrder(p_map, p_player); // No valid move, try again
    }
}
