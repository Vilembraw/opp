import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SvgScene {
    private List<Polygon> polygones = new ArrayList<Polygon>();


    public SvgScene() {
    }

    public void addPolygone(Polygon p){
        polygones.add(p);
    }

    public String toSVG(){
        StringBuilder sb = new StringBuilder();
        for(Polygon p : polygones){
            sb.append(p.toSVG());
            sb.append('\n');
        }
        return sb.toString();
    }

    public BoundingBox boundingBox(){
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;

        double maxY = Double.POSITIVE_INFINITY;
        double minY = Double.NEGATIVE_INFINITY;

        for(Polygon p : polygones){
            BoundingBox bb = p.boundingBox();
            minX = Math.min(minX, bb.x());
            minY = Math.min(minY, bb.x());
            maxX = Math.max(maxX, bb.x() + bb.height());
            maxY = Math.max(maxY, bb.x() + bb.height());
        }
        return new BoundingBox(minX, minY, maxX-minX, maxY-minY);
    }

//    public void save(String filePath){
//        try {
//            FileWriter writer = new FileWriter(filePath);
//            writer.write("<svg viewBox="boundingBox().x(), boundingBox().y(), boundingBox().height(), boundingBox().width());
//            writer.write("</svg>")
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
