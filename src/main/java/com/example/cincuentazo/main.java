package com.example.cincuentazo;

import com.example.cincuentazo.View.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainMenuView mainMenu = MainMenuView.getInstance();
    }

    public static void main(String[] args) {
        launch(args);
    }
}