import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
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

                        try {
                            p1.adopt(p);
                        } catch (ParentingAgeException e) {
                            System.out.println(e.getMessage());
                            System.out.printf("Y/N (deafualt)");
                            Scanner scanner = new Scanner(System.in);
                            if(scanner.nextLine().equalsIgnoreCase("y")){
                                e.getParent().getChildren().add(e.getChild());
                            };
                        }
//                        System.out.println(p1);

                    }  if(p2 != null){
                        try {
                            p2.adopt(p);
                        } catch (ParentingAgeException e) {
                            System.out.println(e.getMessage());
                            System.out.printf("Y/N (deafualt)");
                            Scanner scanner = new Scanner(System.in);
                            if(scanner.nextLine().equalsIgnoreCase("y")){
                                e.getParent().getChildren().add(e.getChild());
                            };
                        }
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

    public boolean adopt(Person person) throws ParentingAgeException {
        if(person.equals(this)) {
            return false;
        }

        if (this.death != null && !this.death.isAfter(person.birth)) {
            throw new ParentingAgeException("Parent's death must be after child's birth.", this, person);
        }

        if (ChronoUnit.YEARS.between(this.birth, person.birth) < 15) {
            throw new ParentingAgeException("Parent must be at least 15 years older than the child.", this, person);
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


    public static void toBinaryFile(List<Person> personList, String path){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            oos.writeObject(personList);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<Person> fromBinaryFile(String path){
        List<Person> personList = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
            Object o = in.readObject();
            return (List<Person>) o;
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }


    private String getUMLObject(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("object \"");
        stringBuilder.append(getFullName());
        stringBuilder.append("\"{\n");
        stringBuilder.append("\tbirth = ");
        stringBuilder.append(birth);
        stringBuilder.append("\n");
        stringBuilder.append("\tdeath = ");
        if (death != null) {
            stringBuilder.append(death);
        }
        stringBuilder.append("\n");
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    public static String umlFromList(List<Person> personList){
        StringBuilder stringBuilder = new StringBuilder();

        for(Person person : personList){
            stringBuilder.append(person.getUMLObject());
        }

        for(Person person : personList){
            for(Person child : person.getChildren()){
                stringBuilder.append("\"");
                stringBuilder.append(child.getFullName());
                stringBuilder.append("\"");
                stringBuilder.append(" --> ");
                stringBuilder.append("\"");
                stringBuilder.append(person.getFullName());
                stringBuilder.append("\"");
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String getSurname() {
        return surname;
    }

    public static List<Person> selectSurnames(List<Person> personList, String substring){
        List<Person> result = new ArrayList<>();
//        for(Person person : personList){
//            if(person.getSurname().toLowerCase().contains(substring.toLowerCase())){
//                result.add(person);
//            }
//
//        }
//        return result;
        return personList.stream().filter( p -> p.getSurname().toLowerCase().contains(substring.toLowerCase())).collect(Collectors.toList());
    }

    public static List<Person> sortedByBirth(List<Person> personList){
        return personList.stream().sorted().collect(Collectors.toList());
    }

    public LocalDate getDeath() {
        return death;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public static List<Person> selectDead(List<Person> personList){
        return personList.stream().filter(p -> p.getDeath() != null).sorted(Comparator.comparing(p -> ChronoUnit.DAYS.between(p.death,p.birth))).collect(Collectors.toList());
    }

    public static Person getOldest(List<Person> personList){
        return personList.stream()
                .filter(p -> p.getDeath() == null)
                .max(Comparator.comparing(p -> ChronoUnit.DAYS.between(p.getBirth(), LocalDate.now())))
                .orElse(null);
    }
}