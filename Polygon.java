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

    @Override
    public String toString() {
        String s = "";
        for(Point point : points){
            s+=point.getX();
            s+=",";
            s+=point.getY();
            s+=" ";
        }
        return s;
    }

    public String toSVG(){
        return "<polygon points=\""+ toString() +"\" style=\"fill:lime;stroke:purple;stroke-width:3\" />";
    }
}
