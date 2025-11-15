package com.example.cincuentazo.Models;

/**
 * Clase que maneja la creacion de una carta asi como el control de su id, valor nominal o valor de juego y su palo
 * @author sergio
 * @version 1.0
 */
public class CartaModel implements ICarta{

    /**
     * Atributos que representan una carta
     */
    private String Id;
    private int ValorNominal;
    private String Palo;


    /**
     * Constructor por defecto de la clase CartaModel
     */
    public CartaModel(){
        this.Id = "";
        this.ValorNominal = 0;
        this.Palo = "";
    }

    /**
     * Constructor que crea una carta con una, identificacion, valor nominal y palo especificos
     * @param Id Id que representa la carta creada
     * @param ValorNominal Valor que toma la carta
     * @param Palo Palo al que pertenece la carta
     */
    public CartaModel(String Id, int ValorNominal, String Palo) {
        this.Id = Id;
        this.ValorNominal = ValorNominal;
        this.Palo = Palo;
    }


    /**
     * Metodo que retorna la identificacion de la carta
     * @return Identificaion de la carta
     */
    @Override
    public String getId(){
        return this.Id;
    }

    /**
     * Metodo que retorna el valor de la carta
     * @return Valor de la carta
     */
    @Override
    public int getValorNominal() {
        return this.ValorNominal;
    }

    /**
     * Metodo que retorna el palo de la carta
     * @return Palo de la carta
     */
    @Override
    public String getPalo() {
        return this.Palo;
    }

    /**
     * Metodo que le asigna una identifiacion especifica a la carta
     * @param id Identificacion que se le asigna a la carta
     */
    @Override
    public void setId(int id){
        this.Id = Integer.toString(id);
    }

    /**
     * Metodo que le asigna un valor específico a la carta
     * @param Valor Valor que toma la carta
     */
    @Override
    public void setValorNominal(int Valor) {
        this.ValorNominal = Valor;
    }

    /**
     * Metodo que le asigna un palo específico a la carta
     * @param Palo Palo que toma la carta
     */
    @Override
    public void setPalo(String Palo) {
        this.Palo = Palo;

    }
}
