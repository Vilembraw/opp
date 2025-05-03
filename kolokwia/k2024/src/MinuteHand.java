import java.time.LocalTime;

public class MinuteHand extends ClockHand{
    //  <line x1="0" y1="0" x2="0" y2="-80" stroke="red" stroke-width="1" />
    Double x;
    Double y;

    @Override
    public void setTime(LocalTime time) {
        double angle = time.getMinute() * 6;
        this.x = 60 * (Math.cos(Math.toRadians(angle - 90)));
        this.y = 60 * (Math.sin(Math.toRadians(angle - 90)));
    }

    @Override
    public String toSVG() {
        //  <!-- Sekundy -->
        //  <line x1="0" y1="0" x2="0" y2="-80" stroke="red" stroke-width="1" />
        StringBuilder sb = new StringBuilder();
        sb.append("<!-- Minuty -->");
        sb.append(String.format("\n<line x1=\"0\" y1=\"0\" x2=\"%.2f\" y2=\"%.2f\" stroke=\"black\" stroke-width=\"2\" \n/>",this.x, this.y));
        return sb.toString();
    }
}
