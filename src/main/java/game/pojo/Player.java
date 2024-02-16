package game.pojo;

import java.util.*;

public class Player {

    String name;
    List<Country> countries;
    int totalArmyCount;
    int d_reinforcements;
    private Queue<Order> d_orderList;

    public Player() {}

    public Player(String name, List<Country> countries, int totalArmyCount) {
        this.name = name;
        this.countries = countries;
        this.totalArmyCount = totalArmyCount;
        this.d_orderList = new LinkedList<>();
        this.d_reinforcements = 5; //  the initial value of reinforcements for all the players
    }

    public Player(String name) {
        this(name, new ArrayList<>(), 0);
    }

    public String getName() {
        return name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public int getTotalArmyCount() {
        return totalArmyCount;
    }
    public int getReinforcements() {
        return d_reinforcements;
    }

    public void setReinforcements(int d_reinforcements) {
        this.d_reinforcements = d_reinforcements;
    }

    public void issue_order(Country p_destination, int p_armyNumber){

       boolean l_state =  this.d_orderList.offer(new DeployOrder(p_destination, p_armyNumber));
        if (l_state) {
            this.d_reinforcements = this.d_reinforcements - p_armyNumber;
        }else {
            System.out.println("Problem with deployment");
        }
    }


    public Order next_order(){
        return this.d_orderList.poll();
    }


    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) other;

        return Objects.equals(otherPlayer.name, this.name)
                && Objects.equals(otherPlayer.countries, this.countries)
                && Objects.equals(otherPlayer.totalArmyCount, this.totalArmyCount);
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}
