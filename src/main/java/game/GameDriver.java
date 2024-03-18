package game;

import game.map.Map;

/** Class responsible for starting the program */
public class GameDriver {

    /**
     * Entry point of the program
     *
     * @param p_args Mandatory arguments
     */
    public static void main(String[] p_args) {
        System.out.println("Hello and welcome!");
        GameEngine l_ge = new GameEngine(new Map());
        l_ge.startGame();
    }
}
