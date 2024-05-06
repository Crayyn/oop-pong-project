module com.example.ooprogproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;

    opens com.example.ooprogproject to javafx.fxml;
    exports com.example.ooprogproject;
}

