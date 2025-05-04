package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City extends Polygon {
    public final Point center;
    private String name;
    boolean isPort;
    Set<Resource.Type> resources;

    public void addResourceInRange(List<Resource> resources, double range) {
        for (Resource resource : resources) {
            if(resource.type != Resource.Type.Fish || (resource.type == Resource.Type.Fish && isPort)) {
                double length = Math.sqrt(Math.pow((resource.location.x - this.center.x), 2) + Math.pow((resource.location.y - this.center.y), 2));

                if (length <= range) {
                    this.resources.add(resource.type);
                }
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(Point center, String name, double wallLength) {
        super(square(center, wallLength));
        this.center = center;
        this.name = name;
        this.resources = new HashSet<Resource.Type>();
    }

    public Point getCenter() {
        return center;
    }

    public String getName() {
        return name;
    }

    private static List<Point> square(Point center, double wallLength) {
        List<Point> points = new ArrayList<>();

        double halfWallLength = wallLength / 2;

        points.add(new Point(center.x - halfWallLength, center.y - halfWallLength)); //lewy dolny
        points.add(new Point(center.x - halfWallLength, center.y + halfWallLength)); //lewy gorny
        points.add(new Point(center.x + halfWallLength, center.y - halfWallLength)); //prawy dolny
        points.add(new Point(center.x + halfWallLength, center.y + halfWallLength)); //prawy gorny


        return points;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if(isPort) {
            sb.append("⚓");

        }
        sb.append(", ");
        return sb.toString();

    }
}
