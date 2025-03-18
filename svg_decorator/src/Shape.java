public interface Shape {
    BoundingBox boundingBox();
    String toSvg(String s);

//    default String toSVG(){
//        return toSVG("");
//    }
}
