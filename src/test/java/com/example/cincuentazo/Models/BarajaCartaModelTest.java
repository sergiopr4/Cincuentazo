package com.example.cincuentazo.Models;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
class BarajaCartaModelTest {


    @Test
    void darCartaTest1() {
        BarajaCartaModel model = new BarajaCartaModel();
        model.setSeed(3);
        assertThrows(IllegalArgumentException.class, model::DarCarta,"La lista esta vacia entonces size retorna 0 que genera un error al calcular un random");
    }

    @Test
    void darCartaTest2() {
        BarajaCartaModel model = new BarajaCartaModel();
        model.CrearBaraja();
        model.setSeed(3);
        /*
        Random rand = new Random(3);
        System.out.println(rand.nextInt(52)); // Carta en la posicion 18

         */

        CartaModel carta = model.DarCarta();
        String id = carta.getId();
        Integer valorNominal = carta.getValorNominal();
        String palo = carta.getPalo();

        assertEquals("6", id);
        assertEquals(6, valorNominal);
        assertEquals("Diamantes", palo);


    }

    @Test
    void cartasRestantesTest1() {
        BarajaCartaModel model = new BarajaCartaModel();
        assertEquals(0,model.CartasRestantes());
    }

    @Test
    void cartasRestantesTest2() {
        BarajaCartaModel model = new BarajaCartaModel();
        model.CrearBaraja();
        assertEquals(52,model.CartasRestantes());
    }

    @Test
    void agregarCartasAlFinalTest() {
        BarajaCartaModel model = new BarajaCartaModel();
        List<CartaModel> cartas = new ArrayList<>();
        cartas.add(new CartaModel("id",0,"palo"));
        model.agregarCartasAlFinal(cartas);
        List<CartaModel> baraja = model.getBarajaCarta();
        assertEquals(1,baraja.size());
        assertEquals("id",baraja.get(0).getId());
        assertEquals(0,baraja.get(0).getValorNominal());
        assertEquals("palo",baraja.get(0).getPalo());

    }

    @Test
    void mezclarBarajaTest() {
        BarajaCartaModel model = new BarajaCartaModel();
        List<CartaModel> baraja = model.getBarajaCarta();
        baraja.add(new CartaModel("id1",1,"palo1"));
        baraja.add(new CartaModel("id2",2,"palo2"));
        baraja.add(new CartaModel("id3",3,"palo3"));
        baraja.add(new CartaModel("id4",4,"palo4"));
        baraja.add(new CartaModel("id5",5,"palo5"));

        List<CartaModel> barajacopia = model.getBarajaCarta();

        List<CartaModel> mezcla = model.MezclarBaraja();

        assertNotNull(mezcla, "La mezcla no debe ser null");
        assertEquals(5, mezcla.size(), "Debe mantener el mismo tamaño");
        assertTrue(mezcla.containsAll(barajacopia), "Debe contener todas las mismas cartas");

    }

    @Test
    void crearBarajaTest() {
        BarajaCartaModel model = new BarajaCartaModel();
        model.CrearBaraja();
        List<CartaModel> baraja = model.getBarajaCarta();
        assertNotNull(baraja, "La baraja no debe ser null");
        assertEquals(52,baraja.size(), "Deben haber 52 cartas");
        List<CartaModel> cartas = new ArrayList<>();

        cartas.add(new CartaModel("9",0,"Corazones"));
        cartas.add(new CartaModel("9",0,"Diamantes"));
        cartas.add(new CartaModel("9",0,"Treboles"));
        cartas.add(new CartaModel("9",0,"Picas"));

        // Verificar una por una
        for (CartaModel cartaBuscada : cartas) {
            boolean encontrada = false;

            for (CartaModel cartaBaraja : baraja) {
                if (cartaBaraja.getId().equals(cartaBuscada.getId()) &&
                        cartaBaraja.getValorNominal() == cartaBuscada.getValorNominal() &&
                        cartaBaraja.getPalo().equals(cartaBuscada.getPalo())) {
                    encontrada = true;
                    break;
                }
            }

            assertTrue(encontrada,
                    "La carta " + cartaBuscada.getId() + " de " + cartaBuscada.getPalo() +
                            " debe existir en la baraja");


        }
    }

    @Test
    void getBarajaCartaTest() {
        BarajaCartaModel model = new BarajaCartaModel();

        assertNotNull(model.getBarajaCarta(), "La lista no debe ser null");
        assertEquals(0, model.getBarajaCarta().size(), "La baraja debe iniciar vacía");


        model.CrearBaraja();
        List<CartaModel> baraja = model.getBarajaCarta();


        assertNotNull(baraja, "La baraja no debe ser null después de crearla");
        assertEquals(52, baraja.size(), "La baraja debe tener 52 cartas");
    }
}