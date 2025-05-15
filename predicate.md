# Predykaty (Predicates) w Javie

## Podstawy interfejsu `Predicate<T>`

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
    
    // Metody domyślne
    default Predicate<T> and(Predicate<? super T> other) { /* ... */ }
    default Predicate<T> negate() { /* ... */ }
    default Predicate<T> or(Predicate<? super T> other) { /* ... */ }
    
    // Metoda statyczna
    static <T> Predicate<T> isEqual(Object targetRef) { /* ... */ }
}
```

## 1. Proste filtrowanie listy
```java
List<String> names = Arrays.asList("Anna", "Jan", "Maria", "Piotr", "Agnieszka");

Predicate<String> startsWithA = name -> name.startsWith("A");
List<String> filteredNames = names.stream()
                                .filter(startsWithA)
                                .collect(Collectors.toList());
// Wynik: ["Anna", "Agnieszka"]
```

## 2. Łączenie predykatów

```java
Predicate<Integer> isEven = num -> num % 2 == 0;
Predicate<Integer> isGreaterThan10 = num -> num > 10;

// AND
Predicate<Integer> isEvenAndGreaterThan10 = isEven.and(isGreaterThan10);

// OR
Predicate<Integer> isEvenOrGreaterThan10 = isEven.or(isGreaterThan10);

// NEGATE
Predicate<Integer> isNotEven = isEven.negate();

```

## 1. Implementacja interfejsu Predicate
```java
public class LongNamePredicate implements Predicate<String> {
    private final int minLength;
    
    public LongNamePredicate(int minLength) {
        this.minLength = minLength;
    }
    
    @Override
    public boolean test(String name) {
        return name.length() >= minLength;
    }
}


public class PredicateFactory {
    public static Predicate<String> createLengthPredicate(int minLength) {
        return s -> s.length() >= minLength;
    }
}


public class UserPredicates {
    public static Predicate<User> isEligibleForDiscount() {
        return isAdult().and(isActive()).and(hasPremiumSubscription());
    }
}


```

