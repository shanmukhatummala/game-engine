package game.pojo;

import game.commands.Command;
import game.map.Map;
import game.order.Advance;
import game.order.Airlift;
import game.order.Blockade;
import game.order.Bomb;
import game.order.Deploy;
import game.order.Negotiate;
import game.order.Order;
import game.util.IssueOrderHelper;

import java.util.*;

/** Player is a POJO representing a player */
public class Player {

    /** Card enum that stores all the possible cards */
    public enum Card {
        /** Represents the Bomb card */
        BOMB,
        /** Represents the Blockade card */
        BLOCKADE,
        /** Represents the Airlift card */
        AIRLIFT,
        /** Represents the Diplomacy card */
        DIPLOMACY
    }

    private final String d_name;
    private final List<Country> d_countries;
    private int d_reinforcements;
    private final Queue<Order> d_orderList;
    private final List<Card> d_cards;
    private final Set<String> d_negotiatedPlayers;

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
        this.d_reinforcements = 0; //  the initial value of reinforcements for all the players
        this.d_cards = new ArrayList<>();
        this.d_negotiatedPlayers = new HashSet<>();
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
     * Getter for Negotiated players
     *
     * @return Names of players involved in negotiation
     */
    public Set<String> getD_negotiatedPlayers() {
        return d_negotiatedPlayers;
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

    /** Handles the order issued by the Player in the game */
    public void issue_order() {

        Map l_map = IssueOrderHelper.getMap();
        Command l_command = IssueOrderHelper.getCommand();

        String commandType = l_command.getD_commandType();

        if ("deploy".equals(commandType)) {
            String l_countryName = l_command.getD_args().get(0);
            int l_numArmies = Integer.parseInt(l_command.getD_args().get(1));
            d_orderList.add(new Deploy(l_countryName, this, l_numArmies, l_map));
        } else if ("bomb".equals(commandType)) {
            String l_targetCountryString = l_command.getD_args().get(0);
            d_orderList.add(new Bomb(l_targetCountryString, this, l_map));
        } else if ("advance".equals(commandType)) {
            String l_source = l_command.getD_args().get(0);
            String l_target = l_command.getD_args().get(1);
            int l_numArmies = Integer.parseInt(l_command.getD_args().get(2));
            d_orderList.add(new Advance(l_target, l_source, this, l_numArmies, l_map));
        } else if ("blockade".equals(commandType)) {
            String l_targetCountryString = l_command.getD_args().get(0);
            d_orderList.add(new Blockade(l_targetCountryString, this, l_map));
        } else if ("negotiate".equals(commandType)) {
            String l_targetPlayerName = l_command.getD_args().get(0);
            d_orderList.add(new Negotiate(this, l_targetPlayerName, l_map));
        } else if ("airlift".equals(commandType)) {
            String l_source = l_command.getD_args().get(0);
            String l_target = l_command.getD_args().get(1);
            int l_numArmies = Integer.parseInt(l_command.getD_args().get(2));
            d_orderList.add(new Airlift(l_target, l_source, this, l_numArmies, l_map));
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
                && Objects.equals(l_otherPlayer.d_cards, this.d_cards)
                && Objects.equals(l_otherPlayer.d_negotiatedPlayers, this.d_negotiatedPlayers);
    }

    /**
     * Gives the hashcode of a player object
     *
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                d_name, d_countries, d_reinforcements, d_orderList, d_cards, d_negotiatedPlayers);
    }
}
