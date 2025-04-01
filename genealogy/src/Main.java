import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            Person.fromCSV("family.csv");
        } catch (AmbiguousPersonException e) {
            System.err.println(e.getMessage());
        }
    }
}