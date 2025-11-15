package com.example.cincuentazo.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa la logica de los datos dentro del juego (Modelo).
 * Este es el "cerebro" que maneja el estado de la partida, la baraja,
 * la suma en mesa, y las acciones de todos los participantes (jugador y bots).
 * @author sergio
 * @version 2.0
 */
public class JuegoModel {

    private BarajaCartaModel baraja;
    private int sumaMesa = 0;
    private CartaModel ultimaCartaJugada = null;
    private List<CartaModel> manoJugador = new ArrayList<>();
    private List<BotModel> bots = new ArrayList<>();
    private List<CartaModel>CartasUsadas=new ArrayList<>();
    private boolean estado=true;
    /**
     * Constructor de la clase JuegoModel.
     * Inicializa la baraja de cartas.
     */
    public JuegoModel() {
        baraja = new BarajaCartaModel();
    }

    /**
     * Prepara e inicia una nueva partida.
     * Es llamado por el Controlador al iniciar el juego.
     * @param numBots El número de bots seleccionados por el jugador.
     */
    public void iniciarPartida(int numBots) {
        baraja.CrearBaraja();

        manoJugador.clear();
        bots.clear();
        sumaMesa = 0;
        this.ultimaCartaJugada = baraja.getBarajaCarta().get(new Random().nextInt(baraja.CartasRestantes()));
        sumaMesa += ultimaCartaJugada.getValorNominal();
        CartasUsadas.add(ultimaCartaJugada);
        for (int i = 0; i < numBots; i++) {
            bots.add(new BotModel(BotModel.Nivel.FACIL));
        }

        repartirCartas();
    }

    /**
     * Reparte 4 cartas al jugador y 4 cartas a cada bot en la partida.
     */
    private void repartirCartas() {
        for (int j = 0; j < 4; j++) {
            manoJugador.add(baraja.DarCarta());
        }

        for (BotModel bot : bots) {
            for (int j = 0; j < 4; j++) {
                bot.recibirCarta(baraja.DarCarta());
            }
        }
    }

    /**
     * Ejecuta la lógica del turno para todos los bots en secuencia.
     * Cada bot verifica si puede jugar, elige una carta y la juega,
     * actualizando la suma y robando una nueva carta.
     */
    public void jugarTurnosBots() {
        System.out.println("--- Turno de Bots ---");
        for (int i = 0; i < bots.size(); i++) {
            BotModel bot = bots.get(i);

            if (!bot.puedeJugar(sumaMesa)) {

                baraja.agregarCartasAlFinal(bot.getMano());
                CartasUsadas.addAll(bot.getMano());
                System.out.println("Bot " + (i+1) + " no puede jugar. Cartas agregadas al mazo: " + bot.getMano().size());
                bot.getMano().clear();
                continue;
            }

            CartaModel cartaElegida = bot.elegirCartaValida(sumaMesa);

            sumaMesa += cartaElegida.getValorNominal();
            this.ultimaCartaJugada = cartaElegida;

            System.out.println("Bot " + (i+1) + " juega: " + cartaElegida.getId() + " | Nueva suma: " + sumaMesa);

            bot.quitarCarta(cartaElegida);
            CartasUsadas.add(cartaElegida);
            if (baraja.CartasRestantes() > 0) {
                bot.recibirCarta(baraja.DarCarta());
            } else {
                System.out.println("¡No quedan cartas en el mazo!, mezclando");
                baraja.agregarCartasAlFinal(CartasUsadas);
                baraja.MezclarBaraja();
                bot.recibirCarta(baraja.DarCarta());
            }
        }
        System.out.println("--- Fin Turno Bots ---");
    }

    /**
     * Metodo que se encarga del turno para un bot específico.
     * Se encarga automaticamente de eliminar a un Bot si ya no puede jugar.
     * @param bot Bot que hace su turno
     */
    public void ejecutarTurnoBot(BotModel bot) {

        if (!bot.puedeJugar(sumaMesa)) {
            baraja.agregarCartasAlFinal(bot.getMano());
            CartasUsadas.addAll(bot.getMano());

            int i = bots.indexOf(bot); // Logging helper
            System.out.println("Bot " + (i+1) + " no puede jugar. Cartas agregadas al mazo: " + bot.getMano().size());
            bot.getMano().clear();
            return; // Turno terminado (sin carta jugada)
        }

        CartaModel cartaElegida = bot.elegirCartaValida(sumaMesa);

        sumaMesa += cartaElegida.getValorNominal();
        this.ultimaCartaJugada = cartaElegida;

        int i = bots.indexOf(bot); // Logging helper
        System.out.println("Bot " + (i+1) + " juega: " + cartaElegida.getId() + " | Nueva suma: " + sumaMesa);

        bot.quitarCarta(cartaElegida);
        CartasUsadas.add(cartaElegida);

        // Lógica de robar nueva carta
        if (baraja.CartasRestantes() > 0) {
            bot.recibirCarta(baraja.DarCarta());
        } else {
            System.out.println("¡No quedan cartas en el mazo!, mezclando");
            baraja.agregarCartasAlFinal(CartasUsadas);
            baraja.MezclarBaraja();
            bot.recibirCarta(baraja.DarCarta());
        }
    }


    //




    /**
     * Procesa la jugada del jugador humano.
     * Valida si la carta jugada es legal (no supera 50).
     * Si lo es, actualiza la suma, quita la carta de la mano y roba una nueva.
     * @param carta La carta que el jugador ha seleccionado.
     * @return true si la jugada fue válida, false si fue ilegal.
     */

    public boolean jugadorJuegaCarta(CartaModel carta) {
        if(estado){
            if (carta.getValorNominal() + sumaMesa > 50) {
                if (!(manoJugador.stream().anyMatch(c -> c.getValorNominal() + sumaMesa <= 50))){
                    System.out.println("Jugador eliminado");
                    /*
                    * DEBE EXISTIR MODO PARA CAMBIAR A PANTALLA FINAL IGUAL CON BOT
                    * QUE MUESTRE SI GANO LA MAQUINA O HUMANO
                    * :))
                    * */


                    baraja.agregarCartasAlFinal(manoJugador);
                    manoJugador.clear();
                    estado=false;
                    return true;
                }else{System.out.println("Jugada ilegal, supera 50");}
                return false;
            }

            sumaMesa += carta.getValorNominal();
            this.ultimaCartaJugada = carta;
            System.out.println("Jugador juega: " + carta.getId() + " | Nueva suma: " + sumaMesa);

            manoJugador.remove(carta);
            CartasUsadas.add(carta);
            estado=false;
            return true;
        }
        return true;
    }

    /**
     * Metodo que se encarga de añadir una carta de la baraja a la mano del jugador
     */
    public void robarCartaJugador(){
        if(!estado){
            if (baraja.CartasRestantes() > 0) {
                manoJugador.add(baraja.DarCarta());
            }else{
                System.out.println("¡No quedan cartas en el mazo!, mezclando");
                baraja.agregarCartasAlFinal(CartasUsadas);
                baraja.MezclarBaraja();
                manoJugador.add(baraja.DarCarta());
            }
            estado=true;
        }
    };

    public enum EstadoPartida {
        PLAYER_WINS,
        PLAYER_LOSES,
        NONE
    }


    /**
     * Metodo que se encarga de verificar si termina la partida.
     * <p>
     *     Verifica las condiciones de ganar o perder y retorna un atributo especifico si el jugador gano o perdio.
     * </p>
     * @return
     */
    public EstadoPartida verificarFinPartida() {

        // 1. Verificar si TODOS los bots se quedaron sin cartas -> jugador gana
        boolean botsSinCartas = true;
        for (BotModel bot : bots) {
            if (!bot.getMano().isEmpty()) {
                botsSinCartas = false;
                break;
            }
        }

        if (botsSinCartas && !manoJugador.isEmpty()) {
            return EstadoPartida.PLAYER_WINS;
        }

        // 2. El jugador NO puede tomar cartas del mazo y ya no tiene cartas -> pierde
        if (manoJugador.isEmpty() && baraja.CartasRestantes() == 0) {
            return EstadoPartida.PLAYER_LOSES;
        }

        // 3. El jugador tiene cartas pero NO tiene jugadas posibles -> pierde
        boolean tieneJugada = false;
        for (CartaModel carta : manoJugador) {
            if (carta.getValorNominal() + sumaMesa <= 50) {
                tieneJugada = true;
                break;
            }
        }
        if (!tieneJugada) {
            return EstadoPartida.PLAYER_LOSES;
        }


        // 4. Si no ocurre nada → la partida continúa
        return EstadoPartida.NONE;
    }



    /**
     * Metodo que retorna la suma actual en la mesa.
     * @return El valor entero de la sumaMesa.
     */
    public int getSumaMesa() {
        return sumaMesa;
    }

    /**
     * Metodo que retorna la lista de bots en la partida.
     * @return Una lista de objetos BotModel.
     */
    public List<BotModel> getBots() {
        return bots;
    }

    /**
     * Metodo que retorna la mano del jugador humano.
     * @return Lista de objetos CartaModel que representa la mano del jugador.
     */
    public List<CartaModel> getManoJugador() {
        return manoJugador;
    }
    /**
     * Retorna la última carta que se jugó en la mesa.
     * @return La CartaModel jugada, o null si es el inicio del juego.
     */
    public CartaModel getUltimaCartaJugada() {
        return ultimaCartaJugada;
    }
}