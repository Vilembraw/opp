public abstract class ShapeDecorator implements Shape{
    private Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    public Shape getDecoratedShape() {
        return decoratedShape;
    }

    @Override
    public BoundingBox boundingBox() {
        if (decoratedShape.boundingBox() != null) {
            return decoratedShape.boundingBox();
        }
        return null;
    }


    @Override
    public String toSvg(String s) {
        if(decoratedShape.boundingBox() != null) {
            return decoratedShape.toSvg(s);
        }
        return "";
    }
}
