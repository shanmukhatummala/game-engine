package game;

import game.map.Map;

public class GameDriver {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        GameEngine ge = new GameEngine(new Map());
        ge.startGame();
    }
}
