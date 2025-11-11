package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la logica de los datos dentro del juego
 * @author sergio
 * @version 1.0
 */
public class JuegoModel {

    /**
     * Atributos Lista que reptresenta la mano del jugador y objeto de la clase BarajaCartaModel
     */
    private List<CartaModel> ManoJugador = new ArrayList<>();
    BarajaCartaModel baraja = new BarajaCartaModel();

    /**
     * Constructor de la clase JuegoModel se encarga de crear una baraja de cartas e inmediatamente
     * entregarle 4 cartas al jugador.
     */
    public JuegoModel() {
        baraja.CrearBaraja();
        for(int i = 0; i < 4; i++){
            ManoJugador.add(baraja.DarCarta());
        }
    }

    /**
     * Metodo que retorna la mano del jugador
     * @return Lista de objetos CartaModel que representa la mano del jugador.
     */
    public List<CartaModel> getManoJugador() {
        return ManoJugador;
    }

    /**
     * Metodo que retorna la baraja de cartas actual del juego
     * @return Lista de objetos CartaModel que representa la baraja de cartas actual del juego.
     */
    public BarajaCartaModel getBaraja() {
        return baraja;
    }
}
