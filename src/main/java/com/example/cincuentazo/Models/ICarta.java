package com.example.cincuentazo.Models;

/**
 *  Interfaz de clase maneja la creacion de una carta
 * @author sergio
 * @version 1.0
 */
public interface ICarta {
    String getId();
    int getValorNominal();
    String getPalo();
    void setId(int id);
    void setValorNominal(int As);
    void setPalo(String Palo);


}
