package game.pojo;

import static game.map.MapHelper.getCountryByName;
import static game.map.MapHelper.getCountryOwner;

import game.commands.Command;
import game.map.Map;
import game.order.Advance_order;
import game.order.Bomb;
import game.order.Deploy;
import game.order.Order;

import java.util.*;

/** Player is a POJO representing a player */
public class Player {

    public enum Card {
        BOMB,
        REINFORCEMENT,
        BLOCKADE,
        AIRLIFT,
        DIPLOMACY
    }

    private final String d_name;
    private final List<Country> d_countries;
    private int d_reinforcements;
    private final Queue<Order> d_orderList;
    private final List<Card> d_cards;

    /**
     * Constructor with player name and countries for Player
     *
     * @param p_name name of the player
     * @param p_countries list of countries that belong to this player
     */
    public Player(String p_name, List<Country> p_countries) {
        this.d_name = p_name;
        this.d_countries = p_countries;
        this.d_orderList = new LinkedList<>();
        this.d_reinforcements = 5; //  the initial value of reinforcements for all the players
        this.d_cards = new ArrayList<>();
    }

    /**
     * Constructor with player name
     *
     * @param p_name name of the player
     */
    public Player(String p_name) {
        this(p_name, new ArrayList<>());
    }

    /**
     * Getter for player name
     *
     * @return name of the player
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * Getter for countries list
     *
     * @return list of countries that belong to the player
     */
    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * Getter for count of reinforcements
     *
     * @return number of reinforcements
     */
    public int getD_reinforcements() {
        return d_reinforcements;
    }

    /**
     * Getter for order list
     *
     * @return queue that contains the orders
     */
    public Queue<Order> getD_orderList() {
        return d_orderList;
    }

    /**
     * Getter for cards
     *
     * @return cards of the player
     */
    public List<Card> getD_cards() {
        return d_cards;
    }

    /**
     * Setter for reinforcement count
     *
     * @param d_reinforcements reinforcement count to set
     */
    public void setD_reinforcements(int d_reinforcements) {
        this.d_reinforcements = d_reinforcements;
    }

    /**
     * Adds a card to the player's cards
     *
     * @param card the card to be added
     */
    public void addCard(Card card) {
        d_cards.add(card);
    }

    public void issue_order(Map p_map, Command p_command) {

        String commandType = p_command.getCommandType();

        if ("deploy".equals(commandType)) {
            String l_countryId = p_command.getArgs().get(0);
            int l_numArmies = Integer.parseInt(p_command.getArgs().get(1));
            d_orderList.add(new Deploy(getCountryByName(p_map, l_countryId), this, l_numArmies));
        } else if ("bomb".equals(commandType)) {
            String l_target = p_command.getArgs().get(0);
            d_orderList.add(new Bomb(getCountryByName(p_map, l_target), this));
        } else if ("advance".equals(commandType)) {
            String l_source = p_command.getArgs().get(0);
            Country l_sourceCountry = getCountryByName(p_map, l_source);
            String l_target = p_command.getArgs().get(1);
            Country l_targetCountry = getCountryByName(p_map, l_target);
            Player l_targetOwner = getCountryOwner(l_targetCountry, p_map.getD_players());
            int l_numArmies = Integer.parseInt(p_command.getArgs().get(2));
            d_orderList.add(
                    new Advance_order(
                            l_targetCountry, l_sourceCountry, l_targetOwner, this, l_numArmies));
        }
    }

    /**
     * @return the next order(first order in the queue) of the player from the order list
     */
    public Order next_order() {
        return this.d_orderList.poll();
    }

    /**
     * Equals method to check the equality between two player objects
     *
     * @param p_other the object to which this object is compared
     * @return true if the contents of the objects are equal
     */
    @Override
    public boolean equals(Object p_other) {

        if (p_other == this) {
            return true;
        }

        if (!(p_other instanceof Player)) {
            return false;
        }

        Player l_otherPlayer = (Player) p_other;

        return Objects.equals(l_otherPlayer.d_name, this.d_name)
                && Objects.equals(l_otherPlayer.d_countries, this.d_countries)
                && Objects.equals(l_otherPlayer.d_reinforcements, this.d_reinforcements)
                && Objects.equals(l_otherPlayer.d_orderList, this.d_orderList)
                && Objects.equals(l_otherPlayer.d_cards, this.d_cards);
    }

    /**
     * Gives the hashcode of a player object
     *
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_name, d_countries, d_reinforcements, d_orderList, d_cards);
    }
}
