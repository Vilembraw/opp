import java.io.BufferedReader;
import java.io.FileReader;
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
                City city = parseLine(br.readLine());
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
}
