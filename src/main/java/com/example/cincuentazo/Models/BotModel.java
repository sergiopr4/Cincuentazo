package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Representa a un jugador máquina (Bot) en el juego.
 * Contiene la lógica de su mano de cartas y su "inteligencia artificial"
 * para tomar decisiones.
 * @version 1.0
 */
public class BotModel {

    /**
     * Define los niveles de dificultad (IA) del bot.
     */
    public enum Nivel {
        FACIL,
        MEDIO,
        DIFICIL
    }

    private Nivel nivel;
    private List<CartaModel> mano = new ArrayList<>();
    private Random rand = new Random();

    /**
     * Constructor que crea un Bot con un nivel de IA específico.
     * @param nivel El nivel de dificultad (FACIL, MEDIO, DIFICIL).
     */
    public BotModel(Nivel nivel) {
        this.nivel = nivel;
    }

    /**
     * Añade una carta a la mano del bot.
     * @param carta La carta recibida (normalmente del mazo).
     */
    public void recibirCarta(CartaModel carta) {
        mano.add(carta);
    }

    /**
     * Quita una carta específica de la mano del bot (porque la ha jugado).
     * @param carta La carta a quitar.
     */
    public void quitarCarta(CartaModel carta) {
        mano.remove(carta);
    }

    /**
     * Verifica si el bot tiene al menos una carta que pueda jugar
     * sin superar la suma de 50 en la mesa.
     * @param sumaMesa La suma actual en la mesa.
     * @return true si el bot puede jugar al menos una carta, false en caso contrario.
     */
    public boolean puedeJugar(int sumaMesa) {
        return mano.stream().anyMatch(c -> c.getValorNominal() + sumaMesa <= 50);
    }

    /**
     * Lógica de IA para decidir qué carta jugar.
     * La decisión se basa en el nivel de dificultad del bot.
     * @param sumaMesa La suma actual en la mesa.
     * @return La CartaModel que el bot ha decidido jugar.
     */
    public CartaModel elegirCartaValida(int sumaMesa) {

        List<CartaModel> jugables = mano.stream()
                .filter(c -> c.getValorNominal() + sumaMesa <= 50)
                .collect(Collectors.toList());

        if (jugables.isEmpty()) {
            return null;
        }

        switch (nivel) {
            case FACIL:
                return jugables.get(rand.nextInt(jugables.size()));

            case DIFICIL:
                return jugables.stream()
                        .min((a, b) -> Integer.compare(a.getValorNominal(), b.getValorNominal()))
                        .orElse(jugables.get(0));

            case MEDIO:
            default:
                if (rand.nextBoolean()) {
                    return jugables.get(rand.nextInt(jugables.size()));
                } else {
                    return jugables.stream()
                            .min((a, b) -> Integer.compare(a.getValorNominal(), b.getValorNominal()))
                            .orElse(jugables.get(0));
                }
        }
    }

    /**
     * Retorna la mano actual del bot.
     * @return Una lista de CartaModel que representa la mano del bot.
     */
    public List<CartaModel> getMano() {
        return mano;
    }

    /**
     * Establece el nivel de dificultad del bot.
     * @param nivel El nuevo nivel de dificultad.
     */
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
}