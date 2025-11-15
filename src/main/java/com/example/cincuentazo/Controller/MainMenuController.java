package com.example.cincuentazo.Controller;

import com.example.cincuentazo.View.JuegoView;
import com.example.cincuentazo.View.MainMenuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 *  * Controlador de JavaFX (Puente) para la vista principal del menu (Bots.fxml)
 *  * Se encarga de conectar las acciones del usuario (clics)
 *  * y de actualizar la Vista (UI)
 *  * @version 2.0
 */
public class MainMenuController {

    @FXML
    private Button ExitButton;

    @FXML
    private Button OneBotButton;

    @FXML
    private Button ThreeBotButton;

    @FXML
    private Button TwoBotButton;

    /**
     * Metodo general para cuando se presiona un boton
     * <p>
     *     En vez de hacer tres metodos para cada boton, se realiza el siguiente metodo general
     *     el cual revisa de que boton proviene el evento para asignar un numero especifico de
     *     bots con el cual iniciar la partida principal.
     * </p>
     * @param event Evento generado al presionar uno de los tres botones de bots
     * @throws IOException Excepcion que se puede dar por el metodo getInstance() de JuegoView
     */
    @FXML
    void OnActionButton(ActionEvent event) throws IOException {
        int numberBots = 0;

        if (event.getSource().equals(OneBotButton)) {
            numberBots = 1;
        } else if (event.getSource() == TwoBotButton) {
            numberBots = 2;
        } else if (event.getSource() == ThreeBotButton) {
            numberBots = 3;
        }

        if (numberBots > 0) {
            System.out.println("Número de bots: " + numberBots);

            // Obtener instancia de JuegoView y su controlador
            JuegoView juego = JuegoView.getInstance();
            JuegoController juegoController = juego.getJuegoController();

            // Configurar la cantidad de bots y reiniciar el juego
            juegoController.setBots(numberBots);
            juegoController.reiniciarJuego();

            // Mostrar la ventana del juego
            juego.show();

            // Cerrar la ventana del menú
            MainMenuView.getInstance().close();
        }
    }


    /**
     * Metodo que revisa si entro el mouse al boton para cambiar el color del boton
     * @param event Evento que se genera cuando el mouse entra al boton
     */
    @FXML
    void OnMouseEntered(MouseEvent event) {
        if (event.getSource() == OneBotButton) {
            OneBotButton.getStyleClass().add("buttonOnMouse");
        }
        if (event.getSource() == TwoBotButton) {
            TwoBotButton.getStyleClass().add("buttonOnMouse");
        }
        if (event.getSource() == ThreeBotButton) {
            ThreeBotButton.getStyleClass().add("buttonOnMouse");
        }
    }

    /**
     * Metodo que revisa si salió el mouse del boton para cambiar el color del boton
     * @param event cEvento generado cuando el Mouse sale del boton
     */
    @FXML
    void OnMouseExited(MouseEvent event) {
        if (event.getSource() == OneBotButton) {
            OneBotButton.getStyleClass().remove("buttonOnMouse");
        }
        if (event.getSource() == TwoBotButton) {
            TwoBotButton.getStyleClass().remove("buttonOnMouse");
        }
        if (event.getSource() == ThreeBotButton) {
            ThreeBotButton.getStyleClass().remove("buttonOnMouse");
        }
    }

    /**
     * Metodo que se encarga del manejo del evento al presionar el boton de salida.
     * @param event Evento generado al presionar el boton de salida
     * @throws IOException Excepcion que se puede generar por el metodo getInstance() de MainMenuView
     */
    @FXML
    void OnActionExitButton(ActionEvent event) throws IOException {
        MainMenuView.getInstance().close();
    }
}