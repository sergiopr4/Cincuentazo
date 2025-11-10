package com.example.cincuentazo;

import com.example.cincuentazo.Models.BarajaCartaModel;
import com.example.cincuentazo.Models.CartaModel;
import com.example.cincuentazo.Models.JuegoModel;
import com.example.cincuentazo.View.MainMenuView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class main extends Application {  // ← Cambia a mayúscula inicial (por convención de clases en Java)
    @Override
    public void start(Stage stage) throws IOException {
        MainMenuView mainMenu = MainMenuView.getInstance();





        //PRUEBA VERIFICACION DE LA BARAJA Y MANO DEL JUGADOR
        JuegoModel juegoModel = new JuegoModel();

        BarajaCartaModel barajaCartaModel = juegoModel.getBaraja();
        List<CartaModel> lista = barajaCartaModel.getBarajaCarta();
        List<CartaModel> manoJugador=juegoModel.getManoJugador();

        for (CartaModel cartaModel : lista) {
            System.out.println("Id: " + cartaModel.getId()+" Palo: "+cartaModel.getPalo()+" ValorNominal: "+cartaModel.getValorNominal()+"\n");

        }
        System.out.println(lista.size()+"\n"); //Siempre 48, pues 4 se mandan a la mano del jugador

        System.out.println("Mano del Jugador: \n");
        for (CartaModel cartaModel : manoJugador) {
            System.out.println("Id: " + cartaModel.getId()+" Palo: "+cartaModel.getPalo()+" ValorNominal: "+cartaModel.getValorNominal()+"\n");
        }


    }
}
