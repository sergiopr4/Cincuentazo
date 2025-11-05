package com.example.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class main extends Application {  // ← Cambia a mayúscula inicial (por convención de clases en Java)
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cincuentazo/Bots.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cincuentazo");
        stage.setScene(scene);
        stage.show();
    }
}
