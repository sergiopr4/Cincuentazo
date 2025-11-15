package com.example.cincuentazo.Models;

/**
 * Interfaz para notificar al Controlador cuando la tarea asíncrona (Turno de Bots) ha finalizado.
 */
public interface BotTurnCompletionListener {

    /**
     * Llamado por el Thread de fondo una vez que la lógica de bots ha terminado.
     */
    void onBotsTurnFinished();

    void onBotTurnCompletedStep();
}
