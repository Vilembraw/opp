import java.util.Locale;

public class TransformDecorator extends ShapeDecorator {
    private String transform;


    private TransformDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public String toSvg(String s) {
        return super.toSvg(String.format(Locale.ENGLISH,"transform=\"%s\" %s ", transform, s) );
    }


    public static class Builder{
        private String transform = "";
        public Builder translate(Vec2 translation){
            this.transform += String.format(Locale.ENGLISH," translate(%f %f) ", translation.x(), translation.y());
            this.transform = this.transform.trim();
            return this;
        }

        public Builder rotate(float angle, Vec2 center){
            this.transform += String.format(Locale.ENGLISH," rotate(%f %f %f) ", angle, center.x(), center.y());
            this.transform = this.transform.trim();
            return this;
        }

        public Builder scale(float x, float y){
            this.transform += String.format(Locale.ENGLISH," scale(%f %f) ", x, y);
            this.transform = this.transform.trim();
            return this;
        }



        public TransformDecorator build(Shape decoratedShape){
            TransformDecorator decorator = new TransformDecorator(decoratedShape);
            decorator.transform = this.transform;
            return decorator;
        }
    }
}
