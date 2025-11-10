package com.example.cincuentazo.View;

import com.example.cincuentazo.Controller.JuegoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JuegoView extends Stage {

    /**
     * Constructor por defecto de la clase JuegoView
     * @throws IOException
     */
    public JuegoView() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/Juego.fxml")
        );
        Parent root = fxmlLoader.load();
        JuegoController juegoController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        this.setTitle("Cincuentazo");
        this.setScene(scene);
        this.show();
        juegoController.showNumBots();

    }

    /**
     * Constructor de la clase JuegoView que le pasa a su controlador
     * un número específico de Bots.
     * @param numBots Número específico de Bots
     * @throws IOException
     */
    public JuegoView(int numBots) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/cincuentazo/Juego.fxml")
        );
        Parent root = fxmlLoader.load();
        JuegoController juegoController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        this.setTitle("Cincuentazo");
        this.setScene(scene);

        juegoController.setBots(numBots);
        this.show();
        juegoController.showNumBots();

    }

    public static JuegoView getInstance() throws IOException {
        if (JuegoViewHolder.INSTANCE == null){
            return JuegoViewHolder.INSTANCE = new JuegoView();
        }else {
            return JuegoViewHolder.INSTANCE;
        }
    }

    /**
     * Metodo que retorna la instancia unica de JuegoView.
     * Recibe un parametro para una cantidad específica de Bots
     * @param numBots Cantidad de Bots
     * @return Instancia de la ventana de JuegoView
     * @throws IOException
     */
    public static JuegoView getInstance(int numBots) throws IOException {
        if (JuegoViewHolder.INSTANCE == null){
            return JuegoViewHolder.INSTANCE = new JuegoView(numBots);
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
}
