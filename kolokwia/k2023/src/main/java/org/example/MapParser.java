package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapParser {
    static public final class Svg {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("rect")
        private List<Map<String, String>> rects;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("polygon")
        private List<Map<String, String>> polygons;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("text")
        private List<Map<String, String>> texts;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("circle")
        private List<Map<String, String>> circles;
    }

    private record Label(Point point, String text) {}
    private List<Label> labels = new ArrayList<>();

    private List<Land> lands = new ArrayList<>();
    private List<City> cities = new ArrayList<>();


    public List<Land> getLands() {
        return lands;
    }



    private void parseText(Map<String, String> params) {
        addLabel(params.get(""), new Point (Double.parseDouble(params.get("x")), Double.parseDouble(params.get("y"))));
    }
    private void parseCity(Map<String, String> params) {
        double wallLength = Double.parseDouble(params.get("width"));
        //<rect height="15" width="15" y="143" x="230" fill="red"/>
        // x,y to lewy gorny
        // x + height/2
        // y - height/2
        double x = Double.parseDouble(params.get("x"));
        double y = Double.parseDouble(params.get("y"));
        double halfWallLength = wallLength / 2;
        Point center = new Point(x + halfWallLength, y - halfWallLength);
        //name ma byc null chwilowo

        addCity(center,null,wallLength);
    }
    private void parseLand(Map<String, String> params) {
        String[] parts = params.get("points").split(" ");
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < parts.length; i += 2) {
            points.add(new Point(Double.parseDouble(parts[i]), Double.parseDouble(parts[i + 1])));
        }

        addLand(points);
    }

    private void addLabel(String text, Point bottomLeft) {
        labels.add(new Label(bottomLeft, text));
    }

    private void addCity(Point center, String name, double wallLength) {
        cities.add(new City(center, name, wallLength));
    }
    private void addLand(List<Point> points) {
        Land land = new Land(points);
        lands.add(land);
    }

    void matchLabelsToTowns() {
        // TODO Krok 14
        for(Label label : labels) {
            double nearestValue = Double.MAX_VALUE;
            City nearestCity = null;

            for(City city : cities) {
                double x_ = Math.abs(label.point.x - city.center.x);
                double y_ = Math.abs(label.point.y - city.center.y);
                double total = x_ + y_;
                if(nearestValue > total) {
                    nearestValue = total;
                    nearestCity = city;
                }
            }

            nearestCity.setName(label.text);
        }
    }

    void addCitiesToLands() {
        // TODO Krok 15
        for(Land land : lands) {
            int counter = 0;
            for (City city : cities) {
                if(land.inside(city.center)){
                    land.addCity(city);
                    counter++;
                }
            }
            System.out.println(counter);
        }
    }

    void parse(String path) {
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(path);
        try {
            Svg svg = xmlMapper.readValue(file, Svg.class);
            for(var item : svg.texts)
                parseText(item);

            for(var item : svg.rects)
                parseCity(item);

            for(var item : svg.polygons)
                parseLand(item);


            // TODO: Krok 13
            matchLabelsToTowns();
            addCitiesToLands();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
