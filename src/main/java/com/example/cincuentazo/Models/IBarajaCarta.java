package com.example.cincuentazo.Models;

/**
 * Interfaz de la clase que representa una Baraja de cartas
 * @author sergio
 * @version 1.0
 */
public interface IBarajaCarta {
    CartaModel DarCarta();
    int CartasRestantes();
    void CrearBaraja();
}
