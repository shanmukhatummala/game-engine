package game;

import game.map.Map;

public class GameDriver {

    public static void main(String[] p_args) {
        System.out.println("Hello and welcome!");
        GameEngine l_ge = new GameEngine(new Map());
        l_ge.startGame();
    }
}
