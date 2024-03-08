module com.example.ooprogproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ooprogproject to javafx.fxml;
    exports com.example.ooprogproject;
}