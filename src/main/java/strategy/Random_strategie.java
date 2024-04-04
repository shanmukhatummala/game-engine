package strategy;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        /** Prompts the user to input a command to create an order */
        System.out.println(
                "Player: "
                        + p_player.getD_name()
                        + ", enter the command "
                        + "(reinforcements available before the start of this round: "
                        + p_player.getD_reinforcements()
                        + (p_player.getD_cards().isEmpty()
                        ? ""
                        : " and cards available before the start of this round: "
                        + p_player.getD_cards())
                        + "):");
        BufferedReader l_bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Command l_command;
        try {
            String l_commandString = l_bufferedReader.readLine();
            l_command = CommandParser.parse(l_commandString).get(0);
            return l_command;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error when reading command. Error message: " + e.getMessage());
        }
        return this.createOrder(p_map, p_player);
    }



    public void RandomDeploy(Player p_player) {

        List<Country> countries = p_player.getD_countries();
        if (!countries.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(countries.size());
            Country randomCountry = countries.get(index);
            int reinforcements = p_player.getD_reinforcements();
            randomCountry.setD_armyCount(randomCountry.getD_armyCount() + reinforcements);
            p_player.setD_reinforcements(0);
            System.out.println("Deployed " + reinforcements + " armies to " + randomCountry.getD_name());
        }
    }

    public void RandomAttack(Map p_map, Player p_player) {
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
                }
            }
        }
    }


    public void RandomMove( Map p_map,Player p_player) {
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
                }
            }
        }
    }

}
