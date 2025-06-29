package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MapParser mapParser = new MapParser();
        mapParser.parse("map.svg");
        List<Land> lands = mapParser.getLands();

        for (Land land : lands) {
            System.out.println(land.toString());
        }

    }
}