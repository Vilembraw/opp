import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AnalogClock extends Clock{
    List<ClockHand> hands = new ArrayList<>();
    public AnalogClock(City city) {
        super(city);
    }

    public void refreshHands(){
        hands.clear();
        HourHand hh = new HourHand();
        hh.setTime(this.time);
        MinuteHand mh = new MinuteHand();
        mh.setTime(this.time);
        SecondHand sh = new SecondHand();
        sh.setTime(this.time);
        hands.add(hh);
        hands.add(mh);
        hands.add(sh);

    }

    public void toSVG(String path){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            refreshHands();

            String s = "<svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "  <!-- Tarcza zegara -->\n" +
                    "  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />\n" +
                    "  <g text-anchor=\"middle\">\n" +
                    "    <text x=\"0\" y=\"-80\" dy=\"6\">12</text>\n" +
                    "    <text x=\"80\" y=\"0\" dy=\"4\">3</text>\n" +
                    "    <text x=\"0\" y=\"80\" dy=\"6\">6</text>\n" +
                    "    <text x=\"-80\" y=\"0\" dy=\"4\">9</text>\n" +
                    "  </g>\n" +
                    "\n";

            bw.write(s);
            for(ClockHand hand : hands){
                bw.write(hand.toSVG());
            }
            bw.write("</svg>\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
