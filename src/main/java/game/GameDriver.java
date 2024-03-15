package game;

import game.commands.Command;
import game.commands.CommandParser;
import game.map.Map;
import game.pojo.Country;
import game.pojo.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static game.map.MapEditor.editMap;
import static game.map.MapLoader.loadMap;
import static game.map.MapShower.showMap;
import static game.map.MapValidator.isMapValid;
import static game.util.FileHelper.createNewFileForMap;
import static game.util.FileHelper.fileExists;

public class GameDriver {


    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        GameEngine ge = new GameEngine(new Map());
        ge.startGame();
    }



}
