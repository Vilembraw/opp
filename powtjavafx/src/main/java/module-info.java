module org.example.powtjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens org.example.powtjavafx to javafx.fxml;
    exports org.example.powtjavafx;
}