package com.example.cincuentazo.View;

import com.example.cincuentazo.Controller.FinJuegoController;
import com.example.cincuentazo.Controller.JuegoController;
import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FinJuegoView extends Stage {

    private final FinJuegoController finJuegoController;

    /**
     * Constructor de clase FinJuegoView se encarga de mostrar la ventana
     * @throws IOException
     */
    public FinJuegoView() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/FinJuego.fxml")
        );
        Parent root = fxmlLoader.load();
        finJuegoController = fxmlLoader.getController();
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
    public static FinJuegoView getInstance() throws IOException {
        if (FinJuegoViewHolder.INSTANCE == null){
            return FinJuegoViewHolder.INSTANCE = new FinJuegoView();
        }else {
            return FinJuegoViewHolder.INSTANCE;
        }
    }

    /**
     * Clase interna que mantiene la instancia única de MainMenuView.
     */
    private static class FinJuegoViewHolder {
        private static FinJuegoView INSTANCE;
    }

    /**
     * Metodo que retorna el controlador de la ventana actual de FinJuegoView
     * @return Controlador de la ventana FinJuegoView
     */
    public FinJuegoController getFinJuegoController() {
        return finJuegoController;
    }
}
