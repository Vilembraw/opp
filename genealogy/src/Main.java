import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Person p = new Person("Jan", "Kowalski", LocalDate.of(1990, 1, 21));
        p.adopt(new Person("Krzystosz", "Krafczyk", LocalDate.of(2010, 1, 21)));
        System.out.println(p.adopt(p));
        Person b = new Person("Grzegorz", "Kowalski", LocalDate.of(2011, 1, 21));
        Person a = new Person("Kamil", "Kowalski", LocalDate.of(2022, 1, 21));
        Person a1 = new Person("Kamil", "Kowalski", LocalDate.of(2025, 1, 21));
        System.out.println(p.adopt(a));
        Family family = new Family();
        family.add(a);
        family.add(b);
        family.add(p);
        family.add(a1);

//        System.out.println(family.get("Grzegorz"));
        System.out.println(family.get("Kamil"));
    }
}