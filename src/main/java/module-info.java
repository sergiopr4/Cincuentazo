module com.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.cincuentazo.Controller to javafx.fxml;
    exports com.example.cincuentazo;
}
