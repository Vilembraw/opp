import java.time.LocalTime;

public class SecondHand extends ClockHand{
    //  <line x1="0" y1="0" x2="0" y2="-80" stroke="red" stroke-width="1" />
    Double x;
    Double y;

    @Override
    public void setTime(LocalTime time) {
        double angle = time.getSecond() * 6;
        this.x = 80 * (Math.cos(Math.toRadians(angle)));
        this.y = 80 * (Math.sin(Math.toRadians(angle)));
    }

    @Override
    public String toSVG() {
        //  <!-- Sekundy -->
        //  <line x1="0" y1="0" x2="0" y2="-80" stroke="red" stroke-width="1" />
        StringBuilder sb = new StringBuilder();
        sb.append("<!-- Sekundy -->");
        sb.append(String.format("\n<line x1=\"0\" y1=\"0\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"red\" stroke-width=\"1\" \n/>",this.x, this.y));
        return sb.toString();
    }
}
