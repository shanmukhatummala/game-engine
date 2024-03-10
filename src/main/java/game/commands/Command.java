package game.commands;
import java.util.List;
public class Command {
    private String commandType;
    private List<String> args;
    private String phase;

    public Command(String commandType, List<String> args, String phase){
        this.commandType = commandType;
        this.args = args;
        this.phase = phase;
    }

    public String getCommandType() {
        return commandType;
    }

    public List<String> getArgs() {
        return args;
    }

    public String getPhase() {
        return phase;
    }



}
