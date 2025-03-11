public class Ellipse extends Shape {
    private Point center;
    private double radius;

    public Ellipse(Style style, Point center, double radius) {
        super(style);
        this.center = center;
        this.radius = radius;
    }


    @Override
    public String toSVG() {
        return "";
    }
}
