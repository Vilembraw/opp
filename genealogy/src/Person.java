import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Person implements Comparable<Person>{
    private String name;
    private String surname;
    private LocalDate birth;
    private Set<Person> children = new HashSet<>();

    public Person(String name, String surname, LocalDate birth) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birth=" + birth +
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