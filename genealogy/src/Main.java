import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        PlantUMLRunner.setPath("/home/vilem/Downloads/plant.jar");


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
                String umldata = Person.umlFromList(
                        p,
                        uml -> uml.replaceFirst("\\{","#Red {"),
                        person -> Person.selectDead(p).contains(person)
                );



                PlantUMLRunner.generate(umldata,"/home/vilem/Downloads/out", "diagram.png");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        } catch (AmbiguousPersonException e) {
            System.err.println(e.getMessage());
        }
    }
}