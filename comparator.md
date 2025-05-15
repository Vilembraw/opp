## 🔁 Komparatory do sortowania

```java
public static Comparator<City> worstTimezoneFit() {
    return (city1, city2) -> {
        double diff1 = city1.getTimeDifference();
        double diff2 = city2.getTimeDifference();
        return Double.compare(diff2, diff1);
    };
}

public static Comparator<City> worstTimezoneFit1() {
    return new Comparator<City>() {
        @Override
        public int compare(City city1, City city2) {
            double diff1 = city1.getTimeDifference();
            double diff2 = city2.getTimeDifference();
            return Double.compare(diff2, diff1);
        }
    };
}


List<City> cities = Arrays.asList(
    new City("Madryt", 12.5, 12.0),
    new City("Paryż", 12.3, 12.0),
    new City("Amsterdam", 12.2, 12.0),
    new City("Ateny", 13.0, 12.0),
    new City("Ryga", 13.1, 12.0),
    new City("Lima", 7.0, 5.0),
    new City("Rio de Janeiro", 9.0, 3.0),
    new City("Sao Paulo", 9.5, 3.0),
    new City("Kair", 14.0, 2.0),
    new City("Sydney", 22.0, 10.0),
    new City("Osaka", 21.0, 9.0)
);

cities.sort(City.worstTimezoneFit());
```
