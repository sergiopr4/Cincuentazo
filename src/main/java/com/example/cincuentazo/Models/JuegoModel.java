package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la logica de los datos dentro del juego (Modelo).
 * Este es el "cerebro" que maneja el estado de la partida, la baraja,
 * la suma en mesa, y las acciones de todos los participantes (jugador y bots).
 * @author sergio
 * @version 2.0
 */
public class JuegoModel {

    private BarajaCartaModel baraja;
    private int sumaMesa = 0;
    private CartaModel ultimaCartaJugada = null; // <-- AÑADE ESTA LÍNEA
    private List<CartaModel> manoJugador = new ArrayList<>();
    private List<BotModel> bots = new ArrayList<>();

    /**
     * Constructor de la clase JuegoModel.
     * Inicializa la baraja de cartas.
     */
    public JuegoModel() {
        baraja = new BarajaCartaModel();
    }

    /**
     * Prepara e inicia una nueva partida.
     * Es llamado por el Controlador al iniciar el juego.
     * @param numBots El número de bots seleccionados por el jugador.
     */
    public void iniciarPartida(int numBots) {
        baraja.CrearBaraja();

        manoJugador.clear();
        bots.clear();
        sumaMesa = 0;
        this.ultimaCartaJugada = null;
        for (int i = 0; i < numBots; i++) {
            bots.add(new BotModel(BotModel.Nivel.FACIL));
        }

        repartirCartas();
    }

    /**
     * Reparte 4 cartas al jugador y 4 cartas a cada bot en la partida.
     */
    private void repartirCartas() {
        for (int j = 0; j < 4; j++) {
            manoJugador.add(baraja.DarCarta());
        }

        for (BotModel bot : bots) {
            for (int j = 0; j < 4; j++) {
                bot.recibirCarta(baraja.DarCarta());
            }
        }
    }

    /**
     * Ejecuta la lógica del turno para todos los bots en secuencia.
     * Cada bot verifica si puede jugar, elige una carta y la juega,
     * actualizando la suma y robando una nueva carta.
     */
    public void jugarTurnosBots() {
        System.out.println("--- Turno de Bots ---");
        for (int i = 0; i < bots.size(); i++) {
            BotModel bot = bots.get(i);

            if (!bot.puedeJugar(sumaMesa)) {
                System.out.println("Bot " + (i+1) + " no puede jugar y queda eliminado.");
                continue;
            }

            CartaModel cartaElegida = bot.elegirCartaValida(sumaMesa);

            sumaMesa += cartaElegida.getValorNominal();
            this.ultimaCartaJugada = cartaElegida;

            System.out.println("Bot " + (i+1) + " juega: " + cartaElegida.getId() + " | Nueva suma: " + sumaMesa);

            bot.quitarCarta(cartaElegida);

            if (baraja.CartasRestantes() > 0) {
                bot.recibirCarta(baraja.DarCarta());
            } else {
                System.out.println("¡No quedan cartas en el mazo!");
            }
        }
        System.out.println("--- Fin Turno Bots ---");
    }

    /**
     * Procesa la jugada del jugador humano.
     * Valida si la carta jugada es legal (no supera 50).
     * Si lo es, actualiza la suma, quita la carta de la mano y roba una nueva.
     * @param carta La carta que el jugador ha seleccionado.
     * @return true si la jugada fue válida, false si fue ilegal.
     */
    public boolean jugadorJuegaCarta(CartaModel carta) {
        if (carta.getValorNominal() + sumaMesa > 50) {
            System.out.println("Jugada ilegal, supera 50");
            return false;
        }

        sumaMesa += carta.getValorNominal();
        this.ultimaCartaJugada = carta;
        System.out.println("Jugador juega: " + carta.getId() + " | Nueva suma: " + sumaMesa);

        manoJugador.remove(carta);

        if (baraja.CartasRestantes() > 0) {
            manoJugador.add(baraja.DarCarta());
        }

        return true;
    }

    /**
     * Metodo que retorna la suma actual en la mesa.
     * @return El valor entero de la sumaMesa.
     */
    public int getSumaMesa() {
        return sumaMesa;
    }

    /**
     * Metodo que retorna la lista de bots en la partida.
     * @return Una lista de objetos BotModel.
     */
    public List<BotModel> getBots() {
        return bots;
    }

    /**
     * Metodo que retorna la mano del jugador humano.
     * @return Lista de objetos CartaModel que representa la mano del jugador.
     */
    public List<CartaModel> getManoJugador() {
        return manoJugador;
    }
    /**
     * Retorna la última carta que se jugó en la mesa.
     * @return La CartaModel jugada, o null si es el inicio del juego.
     */
    public CartaModel getUltimaCartaJugada() {
        return ultimaCartaJugada;
    }
}