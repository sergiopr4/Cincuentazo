package com.example.cincuentazo.Models;

import com.example.cincuentazo.Models.JuegoModel;
import com.example.cincuentazo.Models.BotTurnCompletionListener;
import com.example.cincuentazo.Models.BotModel;
/**
 * Clase que extiende Thread para ejecutar la lógica de los turnos de los bots
 * en un hilo de fondo, notificando al Controlador cuando el proceso termina.
 */

public class BotTurnExecutor extends Thread {

    private final JuegoModel juegoActual;
    private final BotTurnCompletionListener listener;
    // Timer: 1.5 segundos entre las jugadas de cada bot
    private final long delayMs = 1500;

    public BotTurnExecutor(JuegoModel juegoActual, BotTurnCompletionListener listener) {
        this.juegoActual = juegoActual;
        this.listener = listener;
    }

    @Override
    public void run() {
        System.out.println("Hilo de Bots: Iniciando ejecución de turnos, paso a paso.");

        try {
            // 1. ITERAR sobre la lista de bots (obtenida del Modelo)
            for (BotModel bot : juegoActual.getBots()) {

                // 2. Ejecutar la lógica de UN SOLO bot
                juegoActual.ejecutarTurnoBot(bot); // LLAMADA AL NUEVO MÉTODO

                // 3. Pausa (TIMER)
                Thread.sleep(delayMs);

                // 4. Notificación INTERMEDIA para actualizar la UI
                if (listener != null) {
                    listener.onBotTurnCompletedStep();
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo de Bots interrumpido.");
        } catch (Exception e) {
            System.err.println("Error ejecutando lógica de bots: " + e.getMessage());
        } finally {
            // 5. Notificación FINAL
            if (listener != null) {
                listener.onBotsTurnFinished();
            }
            System.out.println("Hilo de Bots: Fin de la ejecución.");
        }
    }
}