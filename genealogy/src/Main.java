import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PlantUMLRunner.setPath("/home/vilem/Downloads/plant.jar");
        String data = "Alice -> Bob";


        try {
            List<Person> p = Person.fromCSV("family.csv");
//            System.out.println(Person.umlFromList(p));
//            System.out.println("\n");
//            System.out.println(Person.selectSurnames(p,"Kowalski"));
//            System.out.println("\n");
//            System.out.println(Person.selectDead(p) +"\n");
            System.out.println(Person.getOldest(p) +"\n");

//            System.out.println(Person.sortedByBirth(p));
            try {
                PlantUMLRunner.generate(Person.umlFromList(p),"/home/vilem/Downloads/out", "diagram.png");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (AmbiguousPersonException e) {
            System.err.println(e.getMessage());
        }
    }
}