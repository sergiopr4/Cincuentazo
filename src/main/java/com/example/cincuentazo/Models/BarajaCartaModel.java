package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarajaCartaModel implements IBarajaCarta {

    private List<CartaModel> barajaCarta = new ArrayList<>();

    Random rand = new Random();

    @Override
    public CartaModel DarCarta() {
        int indice = rand.nextInt(barajaCarta.size());
        return barajaCarta.remove(indice);
    }

    @Override
    public int CartasRestantes() {
        return barajaCarta.size();
    }

    @Override
    public void CrearBaraja() {
        barajaCarta.clear();
        String[] palos = {"Corazones", "Diamantes", "Treboles", "Picas"};
        for ( String palo : palos ) {
            for (int valor = 1; valor <= 13; ++valor){

                int valorNominal = valor;
                String id= Integer.toString(valor);

                if(valor == 9){
                    valorNominal = 0;
                }else if (valor == 11){
                    valorNominal=-10;
                    id="J";
                } else if (valor == 12){
                    valorNominal=-10;
                    id="Q";
                } else if (valor == 13){
                    valorNominal=-10;
                    id="K";
                }

                CartaModel carta = new CartaModel(id, valorNominal, palo);
                barajaCarta.add(carta);

            }
        }
    }

    public List<CartaModel> getBarajaCarta() {
        return barajaCarta;
    }
}
