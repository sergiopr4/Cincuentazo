package com.example.cincuentazo.Models;

import java.util.List;

/**
 * Interfaz de la clase que representa una Baraja de cartas
 * @author sergio
 * @version 1.0
 */
public interface IBarajaCarta {
    CartaModel DarCarta();
    int CartasRestantes();
    void CrearBaraja();
    void agregarCartasAlFinal(List<CartaModel> cartas);
    List<CartaModel> MezclarBaraja();
}
