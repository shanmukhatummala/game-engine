package game.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command given by the user. A command is made up of a commandType (e.g. 'gameplayer',
 * 'editcontinent', etc...) and of different arguments.
 */
public class Command {
    private static String d_commandType;
    private List<String> d_args;

    /**
     * Constructor for a Command with a type and arguments
     *
     * @param p_commandType Type of the command (e.g. 'gameplayer', 'editcontinent', etc...)
     * @param p_args arguments following the type
     */
    public Command(String p_commandType, List<String> p_args) {
        this.d_commandType = p_commandType;
        this.d_args = p_args;
    }

    /**
     * Constructor for a Command with just a type. (e.g. showmap)
     *
     * @param p_commandType Type of the command (e.g. 'gameplayer', 'editcontinent', etc...)
     */
    public Command(String p_commandType) {
        this(p_commandType, new ArrayList<String>());
    }

    /**
     * Gets the type of the command.
     *
     * @return The command type
     */
    public static String getD_commandType() {
        return d_commandType;
    }

    /**
     * Gets the arguments of the command
     *
     * @return The arguments
     */
    public List<String> getD_args() {
        return d_args;
    }

    /** Returns the command as a single string */
    @Override
    public String toString() {
        StringBuilder l_s = new StringBuilder(d_commandType + " ");
        for (int l_i = 0; l_i < d_args.size(); l_i++) {
            l_s.append(d_args.get(l_i));
            if (l_i < d_args.size() - 1) {
                l_s.append(" ");
            }
        }
        return l_s.toString();
    }
}
