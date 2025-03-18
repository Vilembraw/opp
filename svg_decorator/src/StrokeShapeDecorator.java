import java.util.Locale;

public class StrokeShapeDecorator extends ShapeDecorator {
    private String color;
    private double width;

    public StrokeShapeDecorator(Shape shapeDecorator, String color, double width) {
        super(shapeDecorator);
        this.color = color;
        this.width = width;
    }

    @Override
    public String toSvg(String s) {
        return super.toSvg(String.format(Locale.ENGLISH,"stroke=\"%s\" stroke-width=\"%f\" %s ", color, width, s));
    }
}
