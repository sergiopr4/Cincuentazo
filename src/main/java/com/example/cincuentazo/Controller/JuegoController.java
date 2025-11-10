package com.example.cincuentazo.Controller;

public class JuegoController {

    int NumBots=1;

    public void setBots(int num){
        NumBots=num;
    }

    public void showNumBots(){
        System.out.println("Numero de bots del controlador "+NumBots);
    }
}
