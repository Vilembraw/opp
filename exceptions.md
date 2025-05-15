```java
// Prosty wyjątek unchecked
public class MyUncheckedException extends RuntimeException {
    public MyUncheckedException(String message) {
        super(message);
    }
}

// Wyjątek checked
public class MyCheckedException extends Exception {
    public MyCheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
