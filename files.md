```java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReadExample {
    public static void main(String[] args) {
        String path = "sciezka/do/pliku.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas czytania pliku: " + e.getMessage());
        }
    }
}




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteExample {
    public static void main(String[] args) {
        String path = "sciezka/do/pliku.txt";
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("Pierwsza linia tekstu");
            bw.newLine(); // Dodanie nowej linii
            bw.write("Druga linia tekstu");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }
}
```


