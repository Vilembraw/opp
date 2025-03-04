import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        Point p1 = new Point();
        p1.setX(25.0);
        p1.setY(19.0);

        Point point2 = new Point(-30,45);

        System.out.println(p1);
        System.out.println(p1.toSvg());

        Segment seg = new Segment(p1,point2);

        System.out.println(seg.toString());

        p1.setX(0);
        p1.setY(0);
        System.out.println(seg.toString());
        System.out.println(p1);

        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(5,10));
        points.add(new Point(25,50));
        Point doZmiany = new Point(50,70);
        points.add(doZmiany);

        Polygon polygon = new Polygon(points);
        System.out.println(polygon.toSVG());    

        doZmiany.setY(2137);
        points.add(new Point(80,90));
        System.out.println(polygon.toSVG());


//        p1.translate(-6, 5);
//        System.out.println(p1);
//        Point tr = p1.translated(6, -5);
//        System.out.println(tr);
//        // taki sam jak tr
//        System.out.println(p1.translated(6, -5));
//
//        Segment segment = new Segment();
//        segment.a = new Point();
//        segment.a.x = 0.0;
//        segment.a.y = 0.0;
//        segment.b = new Point();
//        segment.b.x = 4.0;
//        segment.b.y = 3.0;
//
//        System.out.println(segment.length());
//        Segment[] segments = new Segment[2];
//        segments[0] = segment;
//
//        segments[1] = new Segment();
//        segments[1].a = new Point();
//        segments[1].b = new Point();
//        segments[1].a.x = 0.0;
//        segments[1].a.y = 0.0;
//        segments[1].b.x = 7.5;
//        segments[1].b.y = 5.0;
//
//        // segment.maxSegment(segments); tak nie robimy, metoda statyczna
//        Segment result = Segment.maxSegment(segments); //wywołujemy na rzecz klasy
//        System.out.println("Najdluższy: "+result.length());

    }


}