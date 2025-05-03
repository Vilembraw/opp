import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        DigitalClock clock1 = new DigitalClock(DigitalClock.Type.h24);
//        DigitalClock clock2 = new DigitalClock(DigitalClock.Type.h12);
//
//        clock1.setTime(12,0,0);
//        clock2.setTime(12,0,0);
//        System.out.println(clock1.toString());
//        System.out.println(clock2.toString());
//
//
//        clock1.setTime(13,0,0);
//        clock2.setTime(13,0,0);
//        System.out.println(clock1.toString());
//        System.out.println(clock2.toString());
//
//        clock1.setTime(0,0,0);
//        clock2.setTime(0,0,0);
//        System.out.println(clock1.toString());
//        System.out.println(clock2.toString());
//
//        clock1.setTime(2,0,0);
//        clock2.setTime(2,0,0);
//        System.out.println(clock1.toString());
//        System.out.println(clock2.toString());

//
        HashMap<String, City> cities = City.parseFile("strefy.csv");
//        System.out.println(cities.size());
//        System.out.println(cities.toString());
//        City lublin = cities.get("Lublin");
//        City londyn = cities.get("Londyn");
//        DigitalClock clock = new DigitalClock(lublin,DigitalClock.Type.h24);
//        clock.setTime(12,0,0);
//        System.out.println(clock.toString());
//
//        System.out.println(lublin.localMeanTime(clock.getTime()));

        List<String> sorted = cities.entrySet().stream().sorted((e1,e2) -> City.worstTimeZoneFit(e2.getValue(),e1.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
        for (String s : sorted) {
            System.out.println(s);
        }
    }

}