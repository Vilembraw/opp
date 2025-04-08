public class ParentingAgeException extends Exception {
    private final Person parent;
    private final Person child;

    public ParentingAgeException(String message, Person parent, Person child) {
        super(message);
        this.parent = parent;
        this.child = child;
    }

    public Person getParent() {
        return parent;
    }

    public Person getChild() {
        return child;
    }
}
