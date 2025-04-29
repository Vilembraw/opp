import java.util.HashMap;

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


        HashMap<String, City> cities = City.parseFile("strefy.csv");
        System.out.println(cities.toString());
        City lublin = cities.get("Lublin");
        City londyn = cities.get("Londyn");
        DigitalClock clock = new DigitalClock(londyn,DigitalClock.Type.h24);
        clock.setTime(23,59,59);
        System.out.println(clock.toString());
        clock.setCity(lublin);
        System.out.println(clock.toString());



    }

}