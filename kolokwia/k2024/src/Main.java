import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        DigitalClock clock1 = new DigitalClock(DigitalClock.Type.h24);
        DigitalClock clock2 = new DigitalClock(DigitalClock.Type.h12);

        clock1.setTime(12,0,0);
        clock2.setTime(12,0,0);
        System.out.println(clock1.toString());
        System.out.println(clock2.toString());


        clock1.setTime(13,0,0);
        clock2.setTime(13,0,0);
        System.out.println(clock1.toString());
        System.out.println(clock2.toString());

        clock1.setTime(0,0,0);
        clock2.setTime(0,0,0);
        System.out.println(clock1.toString());
        System.out.println(clock2.toString());

        clock1.setTime(2,0,0);
        clock2.setTime(2,0,0);
        System.out.println(clock1.toString());
        System.out.println(clock2.toString());


        HashMap<String, City> cities = City.parseFile("strefy.csv");
        System.out.println(cities.toString());
    }

}