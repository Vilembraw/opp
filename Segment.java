import static java.lang.Math.pow;

public class Segment {
    private Point a, b;


    public Segment(Point a, Point b) {
        this.a = new Point(a);
        this.b = new Point(b);
    }

    public double length(){
        return Math.sqrt(pow(a.getX()-b.getX(), 2) + pow(a.getY()-b.getY(), 2));
    }

    public static Segment maxSegment(Segment[] arr){
        if(arr.length == 0)
            return null;

        Segment max = arr[0];
        for(int i=1; i<arr.length; i++){
            if(arr[i].length() > max.length())
                max = arr[i];
        }
        return max;
    }


    @Override
    public String toString() {
        return "Segment{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }


}
