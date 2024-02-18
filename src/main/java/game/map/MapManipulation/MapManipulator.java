package game.map.MapManipulation;

import game.map.Map;

import static game.constants.MapManipulation.*;

public class MapManipulator {
    public void processCommand(String[] args, Map map) {
        switch (args[0]) {
            case EDIT_CONTINENT:
                EditContinentProcessor.process(args, map);
                break;
            case EDIT_COUNTRY:
                EditCountryProcessor.process(args, map);
                break;
            case EDIT_NEIGHBOR:
                EditNeighborProcessor.process(args, map);
                break;
            default:
                System.out.println("Invalid command please try again");
        }
    }
}
