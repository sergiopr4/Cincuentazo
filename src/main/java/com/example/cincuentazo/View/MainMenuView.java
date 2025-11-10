package com.example.cincuentazo.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuView extends Stage {
    public MainMenuView() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/Bots.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        this.setTitle("Cincuentazo");
        this.setScene(scene);

        this.show();
    }

    public static MainMenuView getInstance() throws IOException {
        if (MainMenuViewHolder.INSTANCE == null){
            return MainMenuViewHolder.INSTANCE = new MainMenuView();
        }else {
            return MainMenuViewHolder.INSTANCE;
        }
    }

    /**
     * Clase interna que mantiene la instancia única de MainMenuView.
     */
    private static class MainMenuViewHolder {
        private static MainMenuView INSTANCE;
    }
}
