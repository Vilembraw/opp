public class Text extends Shape {
    private String text;
    private Point point;
    private int textLength;
    private int fontSize;


    public Text(Style style, String text, Point point, int textLength, int fontSize) {
        super(style);
        this.text = text;
        this.point = point;
        this.textLength = textLength;
        this.fontSize = fontSize;
    }

    @Override
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<text ");
        sb.append("x=\"").append(point.getX()).append("\" ");;
        sb.append("y=\"").append(point.getY()).append("\" ");;
        sb.append("textLength=\"").append(textLength).append("\" "); //tutaj jakis dziwny cammelCase
        sb.append("font-size=\"").append(fontSize).append("\" ");
        sb.append(style.toSvg());
        sb.append(">");
        sb.append(text);
        sb.append("</text>");

        return sb.toString();
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(point.getX(), point.getY(), textLength, fontSize);
    }
}

/* TODO: klasa Text dziedzicząca po Shape
 *  - String text
 *  - x, y (lewy górny róg, może być Point)
 *  - textLength (przy okazji szerokość obiektu do BoundingBox)
 *  - fontSize (przy okazji wysokość obiektu)
 *  + konstruktor
 *  + toSvg()
 *    - atrybuty: x, y, textLength, font-size, style (z klasy Style)
 *  + boundingBox()
 */

