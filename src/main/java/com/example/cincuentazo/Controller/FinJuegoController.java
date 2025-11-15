package com.example.cincuentazo.Controller;

import com.example.cincuentazo.View.FinJuegoView;
import com.example.cincuentazo.View.JuegoView;
import com.example.cincuentazo.View.MainMenuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Controlador de JavaFX (Puente) para la vista principal del menu final (FinJuego.fxml).
 * Se encarga de conectar las acciones del usuario (clics) con el Modelo (lógica del juego)
 * y de actualizar la Vista (UI) con los cambios del Modelo.
 * @version 2.0
 */
public class FinJuegoController {



    @FXML
    private Button btnReiniciar;

    @FXML
    private Button btnVolverMenu;

    @FXML
    private Label lblGanador;

    /**
     * Metodo que se genera al generarse la ventana de FinJuegoView
     * donde cambia el color de la letra si el jugador perdio.
     */
    @FXML
    private void initialize(){
        if (lblGanador.getText().equals("PERDISTE")) {
            lblGanador.setStyle("-fx-text-fill: red");
        }
    }

    /**
     * Metodo que se encarga de manejar el evento de presionar el boton de reiniciar la partida.
     * <p>
     *     Vuelve a retornar la instancia ya existente de JuegoView y reinicia los parametros iniciales
     *     manteniendo la cantidad de bots escogida.
     * </p>
     * @param event Evento generado al presionar el boton btnReiniciar
     * @throws IOException Excepcion que se puede generar por el metodo getInstance de JuegoView y FinJuegoView
     */
    @FXML
    void reiniciarJuego(ActionEvent event) throws IOException {
        JuegoView juego = JuegoView.getInstance();
        juego.getJuegoController().reiniciarJuego();
        FinJuegoView.getInstance().close();
        juego.show();

    }
    /**
     * Metodo que se encarga de manejar el evento de presionar el boton de volver al menu.
     * <p>
     *     Cierra la ventana actual de fin de juego y vuelve a mostrar la instancia ya creada del Menu principal
     * </p>
     * @param event Evento generado al presionar el boton btnVolverMenu
     * @throws IOException Excepcion que se puede generar por el metodo getInstance de JuegoView y FinJuegoView
     */
    @FXML
    void volverMenu(ActionEvent event) throws IOException {
        FinJuegoView.getInstance().close();
        MainMenuView.getInstance().show();


    }

    /**
     * Metodo que asigna un texto específico al Label lblGanador
     * @param text Parametro que define el texto del Label lblGanador
     */
    void setLblGanador(String text) {
        lblGanador.setText(text);
    }



}