package game.map;

// import path from MapLoader;

import game.pojo.Continent;
import game.pojo.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static game.map.MapHelper.getCountryWithId;

public class ShowMap {
    public static void showMap(Map map) {
        System.out.println("Continents");
        System.out.println("----------------------------------------");
        // System.out.println(map.getContinents());
        List<Continent> conarr = map.getContinents();
        int conlen = conarr.toArray().length;
        for(int i=0; i<conlen; i++ ) {
            Continent continent = map.getContinents().get(i);
            String name = continent.getName();
            System.out.println(name);
        }

    }
}
