import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Point p1 = new Point();
        p1.setX(0);
        p1.setY(5);

        Point point2 = new Point(5,5);
        Segment a = new Segment(p1,point2);


        Style style = new Style("red","black",1.0);
        Polygon square = Polygon.square(a, style);
        Polygon squareC = new Polygon(square);
        SvgScene scene = new SvgScene();
        scene.addPolygone(square);
        scene.save("square.html");
        System.out.println(square.toSVG());
//        System.out.println(squareC.toSVG());

    }


}