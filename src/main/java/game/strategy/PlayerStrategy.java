package game.strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.io.IOException;

public interface PlayerStrategy {
    public Command createOrder(Map p_map, Player p_player) throws IOException;
}
