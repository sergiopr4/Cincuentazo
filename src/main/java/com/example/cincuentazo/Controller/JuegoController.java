package com.example.cincuentazo.Controller;

import com.example.cincuentazo.Models.CartaModel;
import com.example.cincuentazo.Models.JuegoModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controlador de JavaFX (Puente) para la vista principal del juego (Juego.fxml).
 * Se encarga de conectar las acciones del usuario (clics) con el Modelo (lógica del juego)
 * y de actualizar la Vista (UI) con los cambios del Modelo.
 * @version 2.0
 */
public class JuegoController {

    private JuegoModel logicaJuego = new JuegoModel();

    @FXML
    private TextField txtSumaMesa;

    @FXML
    private HBox manoHumanoContainer;

    @FXML
    private VBox bot1Container;

    @FXML
    private VBox bot2Container;

    @FXML
    private VBox bot3Container;

    @FXML
    private ImageView imgCartaActual;


    /**
     * Método llamado por JuegoView para pasar la cantidad de bots seleccionada
     * e iniciar la lógica de la partida.
     * @param numBots El número de bots (1, 2 o 3).
     */
    public void setBots(int numBots) {
        System.out.println("Controlador: Recibiendo " + numBots + " bots.");

        if (txtSumaMesa == null || manoHumanoContainer == null) {
            System.out.println("¡ERROR FATAL! Las conexiones FXML son NULAS. Revisa los fx:id en Juego.fxml.");
            return;
        }

        logicaJuego.iniciarPartida(numBots);

        actualizarVista();
    }

    /**
     * Sincroniza la interfaz de usuario (Vista) con el estado actual del Modelo.
     * Lee los datos del JuegoModel y los "dibuja" en la pantalla
     * (actualiza la suma y la mano del jugador).
     */
    private void actualizarVista() {
        txtSumaMesa.setText(String.valueOf(logicaJuego.getSumaMesa()));

        manoHumanoContainer.getChildren().clear();

        for (CartaModel carta : logicaJuego.getManoJugador()) {

            Label cartaLabel = new Label(carta.getId() + "\n" + carta.getPalo());
            cartaLabel.setPrefSize(50, 70);
            cartaLabel.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-padding: 5;");

            cartaLabel.setOnMouseClicked(event -> {
                alHacerClicEnCarta(carta);
            });

            manoHumanoContainer.getChildren().add(cartaLabel);
        }
    }

    /**
     * Método de evento que se dispara cuando el jugador hace clic en una carta (Label).
     * @param carta La CartaModel asociada al Label en el que se hizo clic.
     */
    private void alHacerClicEnCarta(CartaModel carta) {
        System.out.println("Controlador: Jugador hizo clic en: " + carta.getId());

        boolean jugadaExitosa = logicaJuego.jugadorJuegaCarta(carta);

        if (jugadaExitosa) {
            logicaJuego.jugarTurnosBots();
            actualizarVista();
        } else {
            System.out.println("Controlador: Jugada ilegal. La vista no se actualiza.");
        }
    }

    /**
     * Método de soporte llamado por JuegoView.
     */
    public void showNumBots() {
        // Este método es requerido por JuegoView.
    }
}