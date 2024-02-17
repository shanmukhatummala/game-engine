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

    public void issue_order(){
        boolean l_commandStateDone = false;
        while(!l_commandStateDone){
            System.out.println("enter the deployment command: ");
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


    public Order next_order(){
        return this.d_orderList.poll();
    }

    private String[] inputUserCommand(){
        Scanner scanner = new Scanner(System.in);
        String l_command = scanner.nextLine();
        return l_command.split(" ");
    }
    private Map<Country, Integer> processDeployCommand(String[] p_command_args){
        if(!"deploy".equals(p_command_args[0])){
            return errorMessage("Invalid command or a command that has yet to be implemented, try again");
        }
        Country l_destination = getCountryByName(p_command_args[1]);
        if(l_destination==null){
            return errorMessage("This country is not owned by the player");
        }
        int l_armyNumber = parseArmyNumber(p_command_args[2]);
        if(l_armyNumber<0){
            return errorMessage("invalid number of armies");
        }
        Map<Country, Integer> l_commandProcessed = new HashMap<>();
        l_commandProcessed.put(l_destination, l_armyNumber);
        return l_commandProcessed;
    }

    private Map<Country, Integer> errorMessage(String p_message) {
        System.out.println(p_message);
        return null;
    }

    private int parseArmyNumber(String armyNumberStr) {
        try {
            return Integer.parseInt(armyNumberStr);
        } catch (NumberFormatException e) {
            return -1; // Indicates an error in parsing
        }
    }


    private Country getCountryByName(String p_name){
        for(Country l_country: this.getCountries()){
            if(l_country.getName().equals(p_name)){
                return l_country;
            }
        }
        return null;
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
