## HashMap

```java
Map<String, Integer> map = new HashMap<>();
// Dodawanie elementów
map.put("klucz", 10); 

// Pobieranie wartości
Integer wartosc = map.get("klucz"); 

// Sprawdzanie istnienia
boolean zawieraKlucz = map.containsKey("klucz");
boolean zawieraWartosc = map.containsValue(10);

// Usuwanie
map.remove("klucz"); 

// Rozmiar
int rozmiar = map.size(); 

// Iteracja
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}


// Sortowanie po kluczach
Map<String, Integer> posortowane = map.entrySet().stream()
    .sorted(Map.Entry.comparingByKey())
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

// Sortowanie po wartościach
Map<String, Integer> posortowaneWart = map.entrySet().stream()
    .sorted(Map.Entry.comparingByValue())
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1, LinkedHashMap::new));


```

## HashSet

```java
Set<String> set = new HashSet<>();
// Dodawanie elementów
set.add("element");

// Sprawdzanie istnienia
boolean zawiera = set.contains("element"); 

// Usuwanie
set.remove("element"); 

// Rozmiar
int rozmiar = set.size(); 

// Iteracja
for (String element : set) {
    System.out.println(element);
}

// Konwersja na posortowaną listę
List<String> posortowanaLista = set.stream()
    .sorted()
    .collect(Collectors.toList());

// Konwersja na TreeSet (automatyczne sortowanie)
Set<String> posortowanySet = new TreeSet<>(set);
```
