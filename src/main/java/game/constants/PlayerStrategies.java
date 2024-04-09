package game.constants;

import game.strategy.Aggressive;
import game.strategy.Benevolent;
import game.strategy.Cheater;
import game.strategy.Human;
import game.strategy.PlayerStrategy;
import game.strategy.RandomStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code PlayerStrategies} class holds the constants defining the various types of player
 * strategies available in the game. These strategies determine the behavior or playing style of a
 * player.
 */
public class PlayerStrategies {

    /** Strategy type for a human player, indicating direct user control. */
    public static final String HUMAN = "human";

    /**
     * Strategy type for an aggressive player, indicating a play style focused on offense and
     * confrontation.
     */
    public static final String AGGRESSIVE = "aggressive";

    /**
     * Strategy type for a benevolent player, indicating a play style focused on defense and
     * support.
     */
    public static final String BENEVOLENT = "benevolent";

    /**
     * Strategy type for a random player, indicating a play style that is unpredictable and varied.
     */
    public static final String RANDOM = "random";

    /**
     * Strategy type for a cheater player, indicating a play style that may not adhere to the
     * standard rules.
     */
    public static final String CHEATER = "cheater";

    /**
     * A mapping of strategy names to their corresponding {@code PlayerStrategy} objects. This map
     * allows for dynamic retrieval of player strategies based on their name.
     */
    public static final Map<String, PlayerStrategy> PLAYER_STRATEGY_MAP = new HashMap<>()
    {{
        put(HUMAN, Human.getHumanStrategy());
        put(AGGRESSIVE, Aggressive.getAggressiveStrategy());
        put(BENEVOLENT, Benevolent.getBenevolentStrategy());
        put(RANDOM, RandomStrategy.getRandomStrategy());
        put(CHEATER, Cheater.getCheaterStrategy());
    }};

    /**
     * A mapping of AI player strategy identifiers to their corresponding display names.
     * This map is used to convert internal strategy names to more user-friendly names
     * that can be displayed in the game's user interface or logs.
     */
    public static final Map<String, String> AI_PLAYER_STRATEGY_TO_NAME = new HashMap<>()
    {{
        put(AGGRESSIVE, StringUtils.capitalize(AGGRESSIVE));
        put(BENEVOLENT, StringUtils.capitalize(BENEVOLENT));
        put(RANDOM, StringUtils.capitalize(RANDOM));
        put(CHEATER, StringUtils.capitalize(CHEATER));
    }};
}
