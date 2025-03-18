import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Shape triangle = new Polygon(new Vec2[]{
                new Vec2(0, 0),
                new Vec2(300, 0),
                new Vec2(150, 250)
        });


        Polygon pentagon = new Polygon(new Vec2[]{
                new Vec2(0, 260),
                new Vec2(100, 460),
                new Vec2(300, 560),
                new Vec2(500, 460),
                new Vec2(600, 260)
        });



        SolidFillShapeDecorator redPentagon = new SolidFillShapeDecorator(pentagon,"red");
        triangle = new StrokeShapeDecorator(triangle, "pink", 5.0);
        triangle = new SolidFillShapeDecorator(triangle,"blue");

        Shape ellipse = new Ellipse(new Vec2(500, 700), 400, 100);
        ellipse = new SolidFillShapeDecorator(ellipse,"green");
        ellipse = new StrokeShapeDecorator(ellipse, "yellow", 3.0);
        SvgScene scene = new SvgScene();
        scene.addShape(triangle);
        scene.addShape(redPentagon);
        scene.addShape(ellipse);
        scene.save("result.svg");


    }
}
