package game;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import game.map.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/** Test class for GameEngine class */
public class GameEngineTest {
    private GameEngine d_gameEngine;
    private Map d_map;

    /** Sets up the initial configuration before the test starts */
    @BeforeEach
    void setUp() {
        d_map = new Map();
        d_gameEngine = new GameEngine(d_map);
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
    }

    /** Tests if startGame takes loadmap command and executes it */
    @Test
    public void shouldTakeLoadMapCommandAndExecute() {
        String l_command = "loadmap new.map\n";
        System.setIn(new ByteArrayInputStream(l_command.getBytes()));
        Thread l_thread = new Thread(() -> d_gameEngine.startGame());
        l_thread.start();
        ByteArrayOutputStream l_output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_output));
        while (!l_output.toString().contains("Loaded the map into Java objects")) {}
        l_thread.stop();

        assertThat(d_map.getD_continents().size(), not(equalTo(0)));
        assertThat(d_map.getD_countries().size(), not(equalTo(0)));
    }

    /** Tests if startGame takes gameplayer command and executes it */
    @Test
    public void shouldTakeGamePlayerCommandAndExecute() {
        String l_command = "gameplayer -add p1 -add p2 -add p3 -remove p1\n";
        System.setIn(new ByteArrayInputStream(l_command.getBytes()));
        Thread l_thread = new Thread(() -> d_gameEngine.startGame());
        l_thread.start();
        ByteArrayOutputStream l_output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_output));
        while (!l_output.toString().contains("Player p1 removed")) {}
        l_thread.stop();

        assertThat(d_map.getD_players().size(), equalTo(2));
        assertThat(d_map.getD_players().get(0).getD_name(), equalTo("p2"));
        assertThat(d_map.getD_players().get(1).getD_name(), equalTo("p3"));
    }
}
