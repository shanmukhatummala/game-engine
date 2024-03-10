package game.commands;
import java.util.List;
public class Command {
    private String commandType;
    private List<String> args;


    public Command(String commandType, List<String> args){
        this.commandType = commandType;
        this.args = args;

    }

    public String getCommandType() {
        return commandType;
    }

    public List<String> getArgs() {
        return args;
    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder(commandType + " ");
        for (int i = 0; i < args.size(); i++) {
            s.append(args.get(i));
            if (i< args.size()-1){
                s.append(" ");
            }
        }
        return s.toString();
    }



}
