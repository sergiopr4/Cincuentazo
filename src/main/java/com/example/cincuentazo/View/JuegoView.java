package com.example.cincuentazo.View;

import com.example.cincuentazo.Controller.JuegoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JuegoView extends Stage {

    private JuegoController juegoController;

    /**
     * Constructor por defecto de la clase JuegoView
     * @throws IOException
     */
    public JuegoView() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/Juego.fxml")
        );
        Parent root = fxmlLoader.load();
        juegoController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        this.setTitle("Cincuentazo");
        this.setScene(scene);
        this.show();
    }

    /**
     * Metodo que retorna la instancia unica de JuegoView.
     * @return Instancia de la ventana de JuegoView
     * @throws IOException
     */
    public static JuegoView getInstance() throws IOException {
        if (JuegoViewHolder.INSTANCE == null){
            return JuegoViewHolder.INSTANCE = new JuegoView();
        }else {
            return JuegoViewHolder.INSTANCE;
        }
    }

    /**
     * Clase interna que mantiene la instancia única de MainMenuView.
     */
    private static class JuegoViewHolder {
        private static JuegoView INSTANCE;
    }

    /**
     * Metodo que retorna el controlador de la ventana actual de JuegoView
     * @return Controlador de la ventana JuegoView
     */
    public JuegoController getJuegoController() {
        return juegoController;
    }
}
