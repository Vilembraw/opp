import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points = new ArrayList<Point>();

    public Polygon(List<Point> points) {
        List<Point> newList = new ArrayList<Point>();
        for(Point p : points){
            newList.add(new Point(p));
        }

        this.points = newList;
    }

    public Polygon(Polygon p){
        List<Point> newList = new ArrayList<Point>();
        for(Point x : p.points){
            newList.add(new Point(x));
        }
        this.points = newList;
    }

    public void setPoint(int index, double x, double y){
        points.get(index).setX(x);
        points.get(index).setY(y);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Point point : points){
            s.append(point.getX());
            s.append(",");
            s.append(point.getY());
            s.append(" ");
        }
        return s.toString();
    }

    public String toSVG(){
        return "<polygon points=\""+ toString() +"\" style=\"fill:lime;stroke:purple;stroke-width:3\" />";
    }


    public BoundingBox boundingBox(){
        if(points.isEmpty()){
            return new BoundingBox(0,0,0,0);
        }
        double minX = points.get(0).getX();
        double maxX = points.get(0).getX();

        double minY = points.get(0).getY();
        double maxY = points.get(0).getY();

        for(int i = 1; i < points.size(); i++){
            if(minX > points.get(i).getX()){
                minX = points.get(i).getX();
            }

            if(maxX < points.get(i).getX()){
                maxX = points.get(i).getX();
            }

            if(minY > points.get(i).getY()){
                minY = points.get(i).getY();
            }

            if(maxY < points.get(i).getY()){
                maxY = points.get(i).getY();
            }
        }
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);

    }
}
