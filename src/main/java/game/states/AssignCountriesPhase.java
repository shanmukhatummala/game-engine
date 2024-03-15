package game.states;

import game.GameEngine;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.util.List;

public class AssignCountriesPhase extends PlaySetupPhase{

    @Override
    public void handleAssignCountries(Map p_map, GameEngine ge) {

        List<Player> players = p_map.getD_players();
        List<Country> countries = p_map.getD_countries();
        boolean countriesAssigned = p_map.assignCountries(players, countries);
        if (!countriesAssigned) {
            //it should throw an exception
//            continue;
        }
        ge.setGamePhase(new OrderCreationPhase());
    }
}
