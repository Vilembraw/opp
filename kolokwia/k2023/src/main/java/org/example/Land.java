package org.example;

import java.util.ArrayList;
import java.util.List;

public class Land extends Polygon {
    private List<City> cities;

    public Land(List<Point> points) {
        super(points);
        this.cities = new ArrayList<>();
    }

    public void addCity(City city) {
        if(this.inside(city.center)){
            checkifPort(city);
            cities.add(city);
        }else{
            throw new RuntimeException(city.getName());
        }
    }

    public void checkifPort(City city){
        for(Point p : city.getPoints()){
            if(!this.inside(p)){
                city.isPort = true;
                return;
            }
        }
        city.isPort = false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(City city : cities){
            sb.append(city.toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
