package strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

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

    private boolean d_deployed = false;
    private boolean d_attacked = false;
    private boolean d_moved = false;

    @Override
    public Command createOrder(Map p_map, Player p_player) {
        if (!d_deployed) {
            d_deployed = true;
            return deployRandomly(p_player);
        } else if (!d_attacked) {
            d_attacked = true;
            return attackRandomly(p_map, p_player);
        } else if (!d_moved) {
            d_moved = true;
            return moveRandomly(p_map, p_player);
        } else {
            // Reset flags for the next round
            d_deployed = false;
            d_attacked = false;
            d_moved = false;
            return new Command("commit");
        }
    }

    private Command deployRandomly(Player p_player) {
        List<Country> countries = p_player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            int reinforcements = p_player.getD_reinforcements();
            System.out.println("Deployed " + reinforcements + " armies to " + randomCountry.getD_name());
            return new Command("deploy");
        }
        return new Command("commit"); // No valid country to deploy, try again
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
                    System.out.println(randomCountry.getD_name() + " attacks " + neighbor.getD_name() +
                            " with " + armiesToAttackWith + " armies");
                    return new Command("attack");
                }
            }
        }
        return new Command("commit"); // No valid attack, try again
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
                if (neighbor != null && getCountryOwner(neighbor, Collections.singletonList(p_player)).equals(p_player)) {
                    int totalArmies = randomCountry.getD_armyCount() + neighbor.getD_armyCount();
                    System.out.println("Moved armies from " + randomCountry.getD_name() + " to " + neighbor.getD_name()+ " "+ totalArmies);
                    return new Command("move");
                }
            }
        }
        return new Command("commit"); // No valid move, try again
    }
}
