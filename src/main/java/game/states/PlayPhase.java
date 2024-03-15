package game.states;

import game.GameEngine;
import game.commands.Command;
import game.map.Map;

import java.util.*;

public class PlayPhase implements Phase {
    @Override
    public void handleLoadMap(Command p_command, Map p_map, GameEngine ge) {}

    @Override
    public void handleGamePlayer(List<Command> p_commandList, Map p_map) {}

    @Override
    public void handleEditMap() {}

    @Override
    public void handleShowMap(Map p_map) {}

    @Override
    public void handleAssignCountries(Map p_map) {}

    @Override
    public void handleSaveMap(String p_fileName, Command p_command, Map p_map) {}

    @Override
    public void handleValidateMap(Map p_map) {}
}
