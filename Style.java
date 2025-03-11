public class Style {
    final String fillColor;
    final String strokeColor;
    final Double strokeWidth;

    public Style(String fillColor, String strokeColor, Double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public Style() {
        this.fillColor = "none";
        this.strokeColor = "black";
        this.strokeWidth = 1.0;
    }

    public String toSVG(){
        StringBuilder sb = new StringBuilder();
        sb.append("style=");
        sb.append("fill:").append(fillColor).append(";");
        sb.append("stroke:").append(strokeColor).append(";");
        sb.append("stroke-width:").append(strokeWidth);
        return sb.toString();

    }
}
