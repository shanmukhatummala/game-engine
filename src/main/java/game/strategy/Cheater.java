package game.strategy;

import game.commands.Command;
import game.map.Map;
import game.order.Order;
import game.pojo.Country;
import game.pojo.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static game.map.MapHelper.getCountryById;
import static game.map.MapHelper.getCountryOwner;

public class Cheater extends PlayerStrategy {

    @Override
    public Command createOrder(Map p_map, Player p_player) {

        List<Country> countries = p_player.getD_countries();

        for (Country country : countries) {
            Set<Integer> neighbors = country.getD_neighborIdList();
            for (Integer neighbor : neighbors) {
                Country neighborCountry = getCountryById(p_map, neighbor);
                Player neighborOwner = getCountryOwner(neighborCountry, p_map.getD_players());
                if (neighborOwner != null && !neighborOwner.equals(p_player)) {
                    System.out.println("Cheater player " + p_player.getD_name() + "conquering their neighbor country : " + neighborCountry.getD_name());
                }
            }
        }
        return null;
    }
}
