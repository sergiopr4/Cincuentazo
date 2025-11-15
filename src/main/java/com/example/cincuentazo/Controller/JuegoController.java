package com.example.cincuentazo.Controller;

import com.example.cincuentazo.Models.BotModel;
import com.example.cincuentazo.Models.CartaModel;
import com.example.cincuentazo.Models.JuegoModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.input.MouseEvent;
import com.example.cincuentazo.Models.BotTurnCompletionListener;
import com.example.cincuentazo.Models.BotTurnExecutor;
import javafx.application.Platform;


/**
 * Controlador de JavaFX (Puente) para la vista principal del juego (Juego.fxml).
 * Se encarga de conectar las acciones del usuario (clics) con el Modelo (lógica del juego)
 * y de actualizar la Vista (UI) con los cambios del Modelo.
 * @version 2.0
 */
public class JuegoController implements BotTurnCompletionListener{

    private JuegoModel logicaJuego = new JuegoModel();
    private Image reversoImg;

    @FXML
    private TextField txtSumaMesa;

    @FXML
    private HBox manoHumanoContainer;

    @FXML
    private HBox bot1ManoContainer;

    @FXML
    private VBox bot2ManoContainer;

    @FXML
    private VBox bot3ManoContainer;

    @FXML
    private ImageView imgCartaActual;

    @FXML
    private ImageView imgMazoRobar;

    // Archivo: JuegoController.java (MODIFICADO)

    @FXML
    void tomarCartaOnClicked(MouseEvent event) {

        System.out.println("Jugador hace clic en mazo para robar");
        if (logicaJuego.getManoJugador().size() <= 3) {
            logicaJuego.robarCartaJugador();
            desiluminarMazo();
            actualizarVista(); // Muestra la carta robada
            iniciarTurnoDeBotsAsincrono();

        } else {
            System.out.println("Debes jugar una carta primero antes de robar");
        }
    }
    /**
     * Método llamado por JuegoView para pasar la cantidad de bots seleccionada
     * e iniciar la lógica de la partida.
     * @param numBots El número de bots (1, 2 o 3).
     */
    public void setBots(int numBots) {
        System.out.println("Controlador: Recibiendo " + numBots + " bots.");

        cargarReverso();// Carga la imagen 'reverso.png'

        // Mostrar la imagen del reverso en el mazo de robar
        if (imgMazoRobar != null) {
            imgMazoRobar.setImage(reversoImg);//reverso de la carta
        }

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
                    try {
                        alHacerClicEnCarta(carta);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
    private void alHacerClicEnCarta(CartaModel carta) throws IOException {
        System.out.println("Controlador: Jugador hizo clic en: " + carta.getId());

        //SELECCIONAR UN AS ESCOGER ENTRE 1 O 10
        if (Objects.equals(carta.getId(), "1")) {
            Integer valor = mostrarOpcionesAs();
            if (valor == null) {
                System.out.println("El usuario canceló.");
                return;
            }
            carta.setValorNominal(valor);
        }

        boolean jugadaExitosa = logicaJuego.jugadorJuegaCarta(carta);
        if (jugadaExitosa) {
            if (logicaJuego.getManoJugador().size() == 3) {
                iluminarMazo();
            }
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

    private void iluminarMazo() {
        imgMazoRobar.setStyle("-fx-effect: dropshadow(three-pass-box, #00ff00, 10, 0.5, 0, 0);");
    }

    private void desiluminarMazo() {
        imgMazoRobar.setStyle("");
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

    public static Integer mostrarOpcionesAs() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleccionar valor del As");
        alert.setHeaderText("¿Qué valor desea asignar al As?");
        alert.setContentText("Seleccione una opción:");

        ButtonType btn1 = new ButtonType("1");
        ButtonType btn10 = new ButtonType("10");
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(btn1, btn10, btnCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == btn1) return 1;
            if (result.get() == btn10) return 10;
        }

        return null; // Usuario canceló
    }


    @Override
    public void onBotsTurnFinished() {
        Platform.runLater(() -> {
            System.out.println("CALLBACK: Bots terminaron. Actualizando UI.");
            actualizarVista();
        });
    }

    @Override
    public void onBotTurnCompletedStep() {
        Platform.runLater(() -> {
            System.out.println("CALLBACK STEP: Un bot terminó. Actualizando vista temporalmente.");
            actualizarVista();
        });
    }

    private void iniciarTurnoDeBotsAsincrono() {
        // Le pasamos el modelo y el listener (el Controlador, 'this')
        BotTurnExecutor executor = new BotTurnExecutor(logicaJuego, this);
        executor.start();
    }
}