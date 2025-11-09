module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jdk.compiler;

    opens com.example.cincuentazo.Controller to javafx.fxml;
    exports com.example.cincuentazo;
}
