package com.example.cincuentazo.Models;

import java.io.Serializable;

public class CartaModel implements ICarta{

    private String Id;
    private int ValorNominal;
    private String Palo;


    public CartaModel(){
        this.ValorNominal = 0;
        this.Palo = "";
    }

    public CartaModel(String Id, int ValorNominal, String Palo) {
        this.Id = Id;
        this.ValorNominal = ValorNominal;
        this.Palo = Palo;
    }


    @Override
    public void TomarValor(int As) {
        if (this.ValorNominal==1 && As == 10){
            this.ValorNominal = 10;
        }
    }

    @Override
    public String getId(){
        return this.Id;
    }

    @Override
    public int getValorNominal() {
        return this.ValorNominal;
    }

    @Override
    public String getPalo() {
        return this.Palo;
    }

    @Override
    public void setId(int id){
        this.Id = Integer.toString(id);
    }

    @Override
    public void setValorNominal(int Valor) {
        this.ValorNominal = Valor;
    }

    @Override
    public void setPalo(String Palo) {
        this.Palo = Palo;

    }
}
