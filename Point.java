public class Point {
    private double x;
    private double y;


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }




    @Override
    public String toString(){
        return "("+x+", "+y+")";
    }

    public String toSvg(){
        return "<circle r=\"5\" cx=\""+x+"\" cy=\""+y+"\" fill=\"black\" />";
    }

    public void translate(double dx, double dy){
        this.x += dx;
        y += dy;
    }

    public Point translated(double dx, double dy){
        Point newPoint = new Point();
        newPoint.x = x+dx;
        newPoint.y = y+dy;
        return newPoint;
    }
}
