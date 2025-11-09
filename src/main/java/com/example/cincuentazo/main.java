package com.example.cincuentazo;

import com.example.cincuentazo.View.MainMenuView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class main extends Application {  // ← Cambia a mayúscula inicial (por convención de clases en Java)
    @Override
    public void start(Stage stage) throws IOException {
        MainMenuView mainMenu = MainMenuView.getInstance();
    }
}
