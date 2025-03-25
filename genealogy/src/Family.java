import java.util.*;

public class Family {
    Map<String, List<Person>> family;

    public Family() {
        this.family = new HashMap<>();
    }

    public void add(Person... people){
        for(Person p : people){
            String key = p.getName();
            if(!this.family.containsKey(key)){
                family.put(key, new ArrayList<>());
            }
            family.get(key).add(p);
        }

    }

    public List<Person> get(String key){
        List<Person> members = family.getOrDefault(key, new ArrayList<>());
        Collections.sort(members);
        return members;

//        List<Person> members = family.getOrDefault(key, new ArrayList<>());
//        return family.getOrDefault(key, new ArrayList<>().stream().sorted().toList());
    }
}
