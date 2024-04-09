package game.strategy;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Benevolent extends PlayerStrategy {

    /** Unique Benevolent object. */
    private static Benevolent d_benevolentStrategy;

    /**
     * Provides access to the strategy, using the Singleton design pattern
     *
     * @return the benevolent strategy
     */
    public static Benevolent getBenevolentStrategy() {
        if (d_benevolentStrategy == null) {
            Benevolent.d_benevolentStrategy = new Benevolent();
        }
        return d_benevolentStrategy;
    }

    /** Private constructor to make sure that the client calls getBenevolentStrategy() */
    private Benevolent() {}
    ;

    /**
     * Creates an order for the player based on the current state of the game. The benevolent player
     * doesn't attack, first it deploys on its weakest country then it tries to move some armies to
     * a weak country.
     *
     * @param p_map the current game map
     * @param p_player the player for whom the order is being created
     * @return the command to be executed
     */
    @Override
    public Command createOrder(Map p_map, Player p_player) {

        boolean l_deployed = p_player.getD_deployed();
        boolean l_moved = p_player.getD_moved();

        if (!l_deployed) {
            p_player.setD_deployed(true);
            return deployCommandOnWeakest(p_player);
        }
        if (!l_moved) {
            p_player.setD_moved(true);
            return moveOnWeakest(p_map, p_player);
        }
        p_player.setD_deployed(false);
        p_player.setD_moved(false);
        return new Command("commit");
    }

    /**
     * Creates a deploy command for the weakest country of the player.
     *
     * @param p_player the player for whom the command is being created
     * @return the deploy command
     */
    private Command deployCommandOnWeakest(Player p_player) {
        String l_countryName = findWeakestCountry(p_player.getD_countries()).getD_name();
        int l_reinforcementsToDeploy = p_player.getD_reinforcements();
        return CommandParser.parse("deploy " + l_countryName + " " + l_reinforcementsToDeploy)
                .get(0);
    }

    /**
     * Creates an advance order to move armies from the strongest country to a weak neighbor.
     *
     * @param p_map the map being played
     * @param p_player the benevolent player
     * @return the command to be executed
     */
    private Command moveOnWeakest(Map p_map, Player p_player) {
        Country l_strongestCountry = findStrongestCountry(p_player.getD_countries());
        int l_minArmies = l_strongestCountry.getD_armyCount();
        Country l_weakestNeighbor = l_strongestCountry;
        // Find the weakest neighbor owned by the player
        for (int l_neighborId : l_strongestCountry.getD_neighborIdList()) {
            Country l_neighbor = getCountryById(p_map, l_neighborId);
            int l_armyCount = l_neighbor.getD_armyCount();
            if (l_armyCount <= l_minArmies
                    && getCountryOwner(l_neighbor, p_map.getD_players()).equals(p_player)) {
                l_minArmies = l_armyCount;
                l_weakestNeighbor = l_neighbor;
            }
        }

        // if there are no weak neighbors, do nothing for the current turn
        if (l_weakestNeighbor.equals(l_strongestCountry)) {
            p_player.setD_deployed(false);
            p_player.setD_moved(false);
            return new Command("commit");
        }
        return CommandParser.parse(
                        "advance "
                                + l_strongestCountry.getD_name()
                                + " "
                                + l_weakestNeighbor.getD_name()
                                + " "
                                + (l_strongestCountry.getD_armyCount() / 2))
                .get(0);
    }

    /**
     * Finds the strongest country among a list of countries.
     *
     * @param p_countries the list of countries to search through
     * @return the strongest country
     */
    private Country findStrongestCountry(List<Country> p_countries) {
        return Collections.max(p_countries, Comparator.comparingInt(Country::getD_armyCount));
    }

    /**
     * Finds the weakest country among a list of countries.
     *
     * @param p_countries the list of countries to search through
     * @return the weakest country
     */
    private Country findWeakestCountry(List<Country> p_countries) {
        return Collections.min(p_countries, Comparator.comparingInt(Country::getD_armyCount));
    }
}
