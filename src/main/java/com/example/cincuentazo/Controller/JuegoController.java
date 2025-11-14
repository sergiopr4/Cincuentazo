package com.example.cincuentazo.Controller;

import com.example.cincuentazo.Models.BotModel; // <-- IMPORT NECESARIO
import com.example.cincuentazo.Models.CartaModel;
import com.example.cincuentazo.Models.JuegoModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; // <-- IMPORT NECESARIO
import javafx.scene.layout.VBox;
import java.util.List; // <-- IMPORT NECESARIO
import java.util.Map;


/**
 * Controlador de JavaFX (Puente) para la vista principal del juego (Juego.fxml).
 * Se encarga de conectar las acciones del usuario (clics) con el Modelo (lógica del juego)
 * y de actualizar la Vista (UI) con los cambios del Modelo.
 * @version 2.0
 */
public class JuegoController {

    private JuegoModel logicaJuego = new JuegoModel();
    private Image reversoImg;

    @FXML
    private TextField txtSumaMesa;

    @FXML
    private HBox manoHumanoContainer;

    @FXML
    private HBox bot1ManoContainer; // Coincide con el HBox del FXML

    @FXML
    private VBox bot2ManoContainer; // Coincide con el VBox del FXML

    @FXML
    private VBox bot3ManoContainer; // Coincide con el VBox del FXML

    @FXML
    private ImageView imgCartaActual;


    /**
     * Método llamado por JuegoView para pasar la cantidad de bots seleccionada
     * e iniciar la lógica de la partida.
     * @param numBots El número de bots (1, 2 o 3).
     */
    public void setBots(int numBots) {
        System.out.println("Controlador: Recibiendo " + numBots + " bots.");

        cargarReverso(); // Carga la imagen 'reverso.png'

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

        // --- Actualizar Mano Humano ---
        manoHumanoContainer.getChildren().clear();
        for (CartaModel carta : logicaJuego.getManoJugador()) {
            // (Tu código de traducción y carga de imagen de la mano... todo esto está perfecto)
            String idTraducido = traducirId(carta.getId());
            String paloTraducido = traducirPalo(carta.getPalo());
            String nombreCarta = idTraducido + "_" + paloTraducido + ".png";
            String rutaImagen = "/com/example/cincuentazo/images/" + nombreCarta;

            try {
                Image img = new Image(getClass().getResourceAsStream(rutaImagen));
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(100);
                imgView.setPreserveRatio(true);
                imgView.setSmooth(true);
                imgView.setOnMouseClicked(event -> {
                    alHacerClicEnCarta(carta);
                });
                manoHumanoContainer.getChildren().add(imgView);
            } catch (Exception e) {
                System.err.println("Error al cargar imagen: " + rutaImagen);
                Label errorLabel = new Label("ERR\n" + carta.getId());
                errorLabel.setPrefSize(50, 70);
                errorLabel.setStyle("-fx-border-color: red; -fx-background-color: white;");
                manoHumanoContainer.getChildren().add(errorLabel);
            }
        }

        // --- Actualizar Carta en Mesa ---
        CartaModel cartaEnMesa = logicaJuego.getUltimaCartaJugada();
        if (cartaEnMesa == null) {
            imgCartaActual.setImage(null);
        } else {
            try {
                String idTraducido = traducirId(cartaEnMesa.getId());
                String paloTraducido = traducirPalo(cartaEnMesa.getPalo());
                String nombreCarta = idTraducido + "_" + paloTraducido + ".png";
                String rutaImagen = "/com/example/cincuentazo/images/" + nombreCarta;

                System.out.println("DEBUG: Buscando imagen en: " + rutaImagen);

                Image img = new Image(getClass().getResourceAsStream(rutaImagen));
                imgCartaActual.setImage(img);
            } catch (Exception e) {
                System.err.println("Error al cargar imagen de la mesa: " + e.getMessage());
                imgCartaActual.setImage(null);
            }
        }

        // --- Actualizar Manos de Bots ---
        List<BotModel> bots = logicaJuego.getBots();

        // Asumimos que los contenedores y los bots están en el mismo orden
        if (bots.size() > 0 && bot1ManoContainer != null) {
            actualizarManoBot(bot1ManoContainer, bots.get(0));
        }
        if (bots.size() > 1 && bot2ManoContainer != null) {
            actualizarManoBot(bot2ManoContainer, bots.get(1));
        }
        if (bots.size() > 2 && bot3ManoContainer != null) {
            actualizarManoBot(bot3ManoContainer, bots.get(2));
        }
    }


    /**
     * Método de evento que se dispara cuando el jugador hace clic en una carta.
     * @param carta La CartaModel asociada a la imagen en la que se hizo clic.
     */
    private void alHacerClicEnCarta(CartaModel carta) {
        System.out.println("Controlador: Jugador hizo clic en: " + carta.getId());

        boolean jugadaExitosa = logicaJuego.jugadorJuegaCarta(carta);

        if (jugadaExitosa) {
            logicaJuego.jugarTurnosBots(); // <-- Nombre corregido
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

    // --- MÉTODOS TRADUCTORES ---

    /**
     * TRADUCTOR 1: ID
     * Convierte el Id del Modelo ("1", "K") al formato de archivo ("ace", "king").
     * @param id El Id de la CartaModel ("1", "J", "K", etc.)
     * @return El nombre de archivo correspondiente ("ace", "jack", "king", etc.)
     */
    private String traducirId(String id) {
        switch (id) {
            case "1":
                return "ace";
            case "J":
                return "jack";
            case "Q":
                return "queen";
            case "K":
                return "king";
            default:
                return id; // "2" se queda como "2", "10" como "10", etc.
        }
    }

    /**
     * TRADUCTOR 2: PALO
     * Convierte los nombres de palos del Modelo ("Corazones")
     * al formato de los archivos de imagen ("of_hearts").
     * @param paloEsp El palo en español (de CartaModel).
     * @return El palo en formato de archivo (ej. "of_clubs").
     */
    private String traducirPalo(String paloEsp) {
        // Mapa estático para la traducción
        Map<String, String> traducciones = Map.of(
                "Corazones", "of_hearts",
                "Diamantes", "of_diamonds",
                "Treboles",  "of_clubs",
                "Picas",     "of_spades"
        );

        return traducciones.getOrDefault(paloEsp, paloEsp);
    }

    // --- MÉTODOS DE VISTA DE BOTS ---

    /**
     * Carga la imagen del reverso de la carta una sola vez.
     */
    private void cargarReverso() {
        try {
            String rutaReverso = "/com/example/cincuentazo/images/reverso.png";
            reversoImg = new Image(getClass().getResourceAsStream(rutaReverso));
            if (reversoImg.isError()) {
                throw new Exception("Error al cargar la imagen del reverso.");
            }
        } catch (Exception e) {
            System.err.println("¡ERROR! No se encontró o no se pudo cargar 'reverso.png'.");
            reversoImg = null;
        }
    }

    /**
     * Dibuja el reverso de las cartas para la mano de un bot.
     * @param botContainer El HBox o VBox de la mano del bot.
     * @param bot El modelo del bot.
     */
    /**
     * Dibuja el reverso de las cartas para la mano de un bot.
     * @param botContainer El HBox o VBox de la mano del bot.
     * @param bot El modelo del bot.
     */
    private void actualizarManoBot(Pane botContainer, BotModel bot) {
        botContainer.getChildren().clear();
        if (reversoImg == null) return; // No se pudo cargar el reverso

        for (int i = 0; i < bot.getMano().size(); i++) {
            ImageView reversoView = new ImageView(reversoImg);
            reversoView.setSmooth(true); // Para que la imagen se vea bien

            // --- CAMBIO DE TAMAÑO ---
            // Los números anteriores (40) eran muy pequeños.

            if (botContainer instanceof HBox) {
                // Bot 1 (Cartas verticales)
                reversoView.setFitHeight(90); // Antes 40
                reversoView.setPreserveRatio(true);
            } else {
                // Bot 2 y 3 (Cartas horizontales/rotadas)
                // No preservamos el ratio para que coincida con el FXML.
                reversoView.setPreserveRatio(false);
                reversoView.setFitWidth(90);  // Antes 40
                reversoView.setFitHeight(65); // Antes 30
            }
            // --- FIN DEL CAMBIO ---

            botContainer.getChildren().add(reversoView);
        }
    }

}