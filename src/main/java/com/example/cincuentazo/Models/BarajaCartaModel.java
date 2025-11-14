package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa una baraja de cartas y sus distintos metodos como crear una baraja, entregar una carta al azar,
 *  retornar la cantidad de cartas restantes y la baraja.
 * @author sergio
 * @version 1.0
 */
public class BarajaCartaModel implements IBarajaCarta {

    /**
     * Atributo Lista que contiene objetos CartaModel que representa la baraja
     */
    private List<CartaModel> barajaCarta = new ArrayList<>();

    Random rand = new Random();

    /**
     * Metodo que retorna una carta al azar de la baraja
     * @return objeto CartaModel que representa una Carta
     */
    @Override
    public CartaModel DarCarta() {
        int indice = rand.nextInt(barajaCarta.size());
        return barajaCarta.remove(indice);
    }

    /**
     * Metodo que retorna la cantidad de cartas restantes en la baraja
     * @return Cantidad de Cartas restantes
     */
    @Override
    public int CartasRestantes() {
        return barajaCarta.size();
    }

    /**
     * Agrega una lista de cartas al final del mazo principal
     * @param cartas Lista de cartas a agregar al final del mazo
     */
    @Override
    public void agregarCartasAlFinal(List<CartaModel> cartas) {
        if (cartas != null && !cartas.isEmpty()) {
            barajaCarta.addAll(cartas);
            System.out.println(cartas.size() + " cartas agregadas al final del mazo");
        }
    }
    @Override
    public List<CartaModel> MezclarBaraja(){
        Collections.shuffle(barajaCarta);
        return barajaCarta;
    }

    /**
     * Metodo que se encarga de crear una baraja de cartas.
     * <p>
     *     Se asegura que la Lista baraja este totalmente vacia, crea un arreglo que contiene los cuatro palos
     *     de las cartas y crea un ciclo de palos donde para cada palo se genera otro ciclo que va iterando del
     *     1 al 13 donde se crean 13 cartas y se añaden a la baraja.
     * </p>
     * <p>
     *     Se verifica para la carta 9 que su valor sea de 0 y -10 para las cartas 11,12 y 13 que representan
     *     las cartas de figura ademas de su respectiva identificacion.
     * </p>
     */
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

    /**
     * Metodo que retorna la baraja de cartas
     * @return Lista de objetos CartaModel que representa la baraja de cartas.
     */
    public List<CartaModel> getBarajaCarta() {
        return barajaCarta;
    }
}
