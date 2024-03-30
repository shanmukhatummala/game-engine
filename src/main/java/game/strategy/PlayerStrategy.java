package game.strategy;

import game.commands.Command;
import game.map.Map;
import game.pojo.Player;

import java.io.IOException;

public abstract class PlayerStrategy {
    public abstract Command createOrder(Map p_map, Player p_player) throws IOException;

    @Override
    public boolean equals(Object p_otherObject) {
        return this.getClass() == p_otherObject.getClass();
    }
}
