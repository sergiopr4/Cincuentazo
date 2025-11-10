package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.List;

public class JuegoModel {
    private List<CartaModel> ManoJugador = new ArrayList<>();
    BarajaCartaModel baraja = new BarajaCartaModel();

    public JuegoModel() {
        baraja.CrearBaraja();
        for(int i = 0; i < 4; i++){
            ManoJugador.add(baraja.DarCarta());
        }
    }

    public List<CartaModel> getManoJugador() {
        return ManoJugador;
    }
    public BarajaCartaModel getBaraja() {
        return baraja;
    }
}
