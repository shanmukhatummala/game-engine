package game.commands;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String d_commandType;
    private List<String> d_args;

    public Command(String d_commandType, List<String> d_args) {
        this.d_commandType = d_commandType;
        this.d_args = d_args;
    }

    public Command(String p_commandType) {
        this(p_commandType, new ArrayList<String>());
    }

    public String getD_commandType() {
        return d_commandType;
    }

    public List<String> getD_args() {
        return d_args;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(d_commandType + " ");
        for (int i = 0; i < d_args.size(); i++) {
            s.append(d_args.get(i));
            if (i < d_args.size() - 1) {
                s.append(" ");
            }
        }
        return s.toString();
    }
}
