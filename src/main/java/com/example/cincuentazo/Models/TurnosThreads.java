package com.example.cincuentazo.Models;

import java.util.ArrayList;

public class TurnosThreads extends Thread{
    public boolean turnoHumano;//true juega humano/false juegan bots
    long tiempoInicial;
    ArrayList<BotModel> bots;
    BotModel botactual;
    JuegoModel juegobots;
    //uso del threads pasando los bots
    public TurnosThreads(boolean turnoHumano,ArrayList<BotModel> bots) {
        this.turnoHumano = turnoHumano;
        this.bots=bots;
    }

    public void setBotActual(BotModel botActual) {//recibe un bot con logica
        this.botactual = botActual;
    }

    public void atenderBot(BotModel botactual) {
        System.out.println(" inicia atención del bot ");
        juegobots.jugarTurnosBots();

        try {
            Thread.sleep( 3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("El bot termina su turno ");
    }

    @Override
    public void run() {
            this.atenderBot(this.botactual);
        System.out.println("Fin del turno de bots");
    }




}