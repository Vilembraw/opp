import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.HashMap;

public class City {
    String name;
    int strefaCzasowa;
    String szerokoscGeograficzna;
    String dlugoscGeograficzna;

    public City(String name, int strefaCzasowa, String szerokoscGeograficzna, String dlugoscGeograficzna) {
        this.name = name;
        this.strefaCzasowa = strefaCzasowa;
        this.szerokoscGeograficzna = szerokoscGeograficzna;
        this.dlugoscGeograficzna = dlugoscGeograficzna;
    }

    public static HashMap<String, City>  parseFile(String path){
        HashMap<String, City> cities = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            //pierwsza linia naglowka skip
            br.readLine();

            String line;
            while((line = br.readLine() )!= null){
                City city = parseLine(line); //fix dla wczytywania
                cities.put(city.name, city);
            }

            return cities;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cities;
    }


    private static City parseLine(String line) {
        String parts[] = line.split(",");
        return new City(
            parts[0],
            Integer.parseInt(parts[1]),
            parts[2],
            parts[3]
        );

    }

    public LocalTime localMeanTime(LocalTime time){
        LocalTime newTime = time.minusHours(this.strefaCzasowa);
        String partsDlugosc[] = this.dlugoscGeograficzna.trim().split(" ");
//        System.out.println(partsDlugosc.length);
                double szerokoscValue = Double.parseDouble(partsDlugosc[0]);
        double offset = szerokoscValue * 4 * 60;
        if(partsDlugosc[1].equals("E")){
            newTime = newTime.plusSeconds((long) offset);
        }else if(partsDlugosc[1].equals("W")){
            newTime = newTime.minusSeconds((long) offset);
        }

        return newTime;

    }

    public int diffTime(){
        LocalTime time = LocalTime.now();
        LocalTime meanTime = localMeanTime(time);

        int minutes =  time.getHour() * 60 + time.getMinute();
        int meanMinutes = meanTime.getHour() * 60 + meanTime.getMinute();
        return Math.abs(minutes-meanMinutes);
    }

    public static int worstTimeZoneFit(City cityA, City cityB){
        return Integer.compare(cityA.diffTime(), cityB.diffTime());
    }
}
