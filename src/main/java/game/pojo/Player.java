package game.pojo;

import java.util.*;

/**
 * Player is a POJO representing a player
 */
public class Player {
    String d_name;
    List<Country> d_countries;
    int d_totalArmyCount;
    int d_reinforcements;
    Queue<Order> d_orderList;
    public static Scanner Scanner;

    public Player() {}


    public Player(String p_name, List<Country> p_countries, int p_totalArmyCount) {
        this.d_name = p_name;
        this.d_countries = p_countries;
        this.d_totalArmyCount = p_totalArmyCount;
        this.d_orderList = new LinkedList<>();
        this.d_reinforcements = 5; //  the initial value of reinforcements for all the players
    }


    public Player(String p_name) {
        this(p_name, new ArrayList<>(), 0);
    }

    public String getD_name() {
        return d_name;
    }

    public List<Country> getD_countries() {
        return d_countries;
    }

    public int getD_totalArmyCount() {
        return d_totalArmyCount;
    }

    public int getD_reinforcements() {
        return d_reinforcements;
    }
    public Queue<Order> getD_orderList() {
        return d_orderList;
    }

    public void setD_reinforcements(int d_reinforcements) {
        this.d_reinforcements = d_reinforcements;
    }


    /**
     * This method will wait for the command and validate this command and then will create a deploy order
     * object on the player list of orders then reduce the number of armies in the player reinforcements
     */
    public void issue_order(){
        boolean l_commandStateDone = false;
        while(!l_commandStateDone){
            String[] l_command_args = inputUserCommand();
            Map<Country, Integer> l_destinationAndArmies = processDeployCommand(l_command_args);
            if(l_destinationAndArmies!=null){
                Country l_destination = l_destinationAndArmies.entrySet().iterator().next().getKey();
                int l_armyNumber = l_destinationAndArmies.entrySet().iterator().next().getValue();
                boolean l_state =  this.d_orderList.offer(new DeployOrder(l_destination, l_armyNumber));
                if (l_state) {
                    this.d_reinforcements = this.d_reinforcements - l_armyNumber;
                }else {
                    System.out.println("Problem with deployment");
                    continue;
                }
                l_commandStateDone = true;
            }
        }
    }


    /**
     *
     * @return the next order(first order in the queue) of the player from the order list
     */
    public Order next_order(){
        return this.d_orderList.poll();
    }

    /**
     * takes the command from the scanner (can be from the user or an input stream as a string from the test)
     * and split it
     * @return array of string
     */
    private String[] inputUserCommand(){
        if(System.in.equals(Scanner)){
            System.out.println("enter the deployment command: ");
        }
        String l_command = Scanner.nextLine();
        return l_command.split(" ");
    }

    /**
     * this method will validate the command and return the destination of deployment and the number of armies
     * @param p_command_args
     * @return HashMap object that holds a Country(destination) as a key and the army number as a value
     */
    private Map<Country, Integer> processDeployCommand(String[] p_command_args){
        if(!"deploy".equals(p_command_args[0])){
            return errorMessage("Invalid command or a command that has yet to be implemented, try again");
        }
        Country l_destination = getCountryByName(p_command_args[1]);
        if(l_destination==null){
            return errorMessage("This country is not owned by the player");
        }
        int l_armyNumber = parseArmyNumber(p_command_args[2]);
        if(l_armyNumber<0 || l_armyNumber > d_reinforcements){
            return errorMessage("invalid number of armies");
        }
        Map<Country, Integer> l_commandProcessed = new HashMap<>();
        l_commandProcessed.put(l_destination, l_armyNumber);
        return l_commandProcessed;
    }

    /**
     * this method print an error message if the validation of the command faild
     * @param p_message error message
     * @return null
     */
    private Map<Country, Integer> errorMessage(String p_message) {
        System.out.println(p_message);
        return null;
    }

    /**
     * this method parse the last argument of the command into an integer (the number of armies)
     * @param armyNumberStr
     * @return army number as Integer
     */
    private int parseArmyNumber(String armyNumberStr) {
        try {
            return Integer.parseInt(armyNumberStr);
        } catch (NumberFormatException e) {
            return -1; // Indicates an error in parsing
        }
    }

    /**
     * this method filter the player countries by name
     * @param p_name the country name
     * @return Country object
     */
    private Country getCountryByName(String p_name){
        for(Country l_country: this.getD_countries()){
            if(l_country.getD_name().equals(p_name)){
                return l_country;
            }
        }
        return null;
    }

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
                && Objects.equals(l_otherPlayer.d_totalArmyCount, this.d_totalArmyCount)
                && Objects.equals(l_otherPlayer.d_reinforcements, this.d_reinforcements)
                && Objects.equals(l_otherPlayer.d_orderList, this.d_orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_name, d_countries, d_totalArmyCount, d_reinforcements, d_orderList);
    }
}
