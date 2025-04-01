import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

public class Person implements Comparable<Person>{
    private String name;
    private String surname;
    private LocalDate birth;
    private LocalDate death;
    private Set<Person> children = new HashSet<>();

    public Person(String name, String surname, LocalDate birth) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
    }

    public Person(String name, String surname, LocalDate birth, LocalDate death) throws NegativeLifespanException {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.death = death;

        if(death != null){
            if(death.isBefore(birth)){
                throw new NegativeLifespanException("death before birth");
            }
        }
    }

    public String getName() {
        return name;
    }

    public Person(String name, String surname, LocalDate birth, Set<Person> children) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.children = children;
    }

    public static Person fromCSVLine(String line) throws NegativeLifespanException {
        String[] parts = line.split(",",-1);
        String name = parts[0].split(" ")[0];
        String surname = parts[0].split(" ")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

//        DateTimeFormatter customFormatter = new DateTimeFormatterBuilder()
//                .appendValue(ChronoField.DAY_OF_MONTH,2)
//                .appendLiteral(".")
//                .appendText(ChronoField.MONTH_OF_YEAR)
//                .appendLiteral(".")
//                .appendValue(ChronoField.YEAR,4)
//                .toFormatter();

        LocalDate birth = LocalDate.parse(parts[1],formatter);
        LocalDate death;
        try{
            death = LocalDate.parse(parts[2],formatter);
        }
        catch(DateTimeParseException e){
            death = null;
//            System.err.println(e.getMessage());
        }


        return new Person(name,surname,birth,death);
    }

    public static List<Person> fromCSV(String path) throws AmbiguousPersonException{
        Map<String,Person> persons = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                try {
                    Person p = fromCSVLine(line);
                    if(persons.containsKey(p.getName())){
                        throw new AmbiguousPersonException(p.getFullName());
                    }
                    persons.put(p.getFullName(), p);
                    String[] parts = line.split(",", -1);
                    Person p1 = persons.get(parts[3]);
                    Person p2 = persons.get(parts[4]);

                    if(p1 != null){
                        p1.adopt(p);
//                        System.out.println(p1);

                    }  if(p2 != null){
                        p2.adopt(p);
//                        System.out.println(p2);
                    }

                    //do zrobienia 5
                } catch (NegativeLifespanException e) {
//                    throw new RuntimeException(e);
                    System.err.println(e.getMessage());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>(persons.values());
    }

    public boolean adopt(Person person) {
        if(person.equals(this)) {
            return false;
        }
        return children.add(person);
    }

    public List<Person> getChildren() {
        //sortuje inplace - nie kopie
//        List<Person> l = children.stream().toList();
//        Collections.sort(l);
//        return l;

        return children.stream().sorted().toList();
    }

    public Person getYoungestChild() {
        if(!children.isEmpty()) {
//            Person youngest = null;
//            for(Person child : children) {
//                if(youngest == null) {
//                    youngest = child;
//                }
//
//                if(child.birth.isAfter(youngest.birth)) {
//                    youngest = child;
//                }
//            }
//            return youngest; 1 sposob

//            return Collections.max(children); 2 sposob

            return children.stream().max(Person::compareTo).get();
        }
        return null;

    }

    public String getFullName(){
        return name + " "  + surname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                ", children=" + children +
                '}';
    }

    @Override
    public int compareTo(Person o) {
//        int diff = this.birth.getYear() - o.birth.getYear();
//        if(diff == 0) {
//            diff = this.birth.getMonthValue() - o.birth.getMonthValue();
//            if(diff == 0) {
//                diff = this.birth.getDayOfMonth() - o.birth.getDayOfMonth();
//            }
//        }
        return this.birth.compareTo(o.birth);
    }


}