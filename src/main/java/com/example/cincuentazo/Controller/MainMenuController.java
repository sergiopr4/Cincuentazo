package com.example.cincuentazo.Controller;

import com.example.cincuentazo.View.JuegoView;
import com.example.cincuentazo.View.MainMenuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button ExitButton;

    @FXML
    private Button OneBotButton;

    @FXML
    private Button ThreeBotButton;

    @FXML
    private Button TwoBotButton;

    @FXML
    void OnActionButton(ActionEvent event) throws IOException {

        if (event.getSource().equals(OneBotButton)){
            JuegoView juego=JuegoView.getInstance();
        }

        if (event.getSource() == TwoBotButton) {
            int numberBots=2;
            System.out.println("numero de bots "+numberBots);
            JuegoView juego=JuegoView.getInstance(numberBots);
        }

        if (event.getSource() == ThreeBotButton) {
            int numberBots = 3;
            System.out.println("numero de bots "+numberBots);
            JuegoView juego=JuegoView.getInstance(numberBots);
        }

        MainMenuView.getInstance().close();

    }

    @FXML
    void OnMouseEntered(MouseEvent event) {
        if (event.getSource() == OneBotButton) {
            OneBotButton.getStyleClass().add("buttonOnMouse");
        }
        if (event.getSource() == TwoBotButton) {
            TwoBotButton.getStyleClass().add("buttonOnMouse");
        }
        if (event.getSource() == ThreeBotButton) {
            ThreeBotButton.getStyleClass().add("buttonOnMouse");
        }
    }

    @FXML
    void OnMouseExited(MouseEvent event) {
        if (event.getSource() == OneBotButton) {
            OneBotButton.getStyleClass().remove("buttonOnMouse");
        }
        if (event.getSource() == TwoBotButton) {
            TwoBotButton.getStyleClass().remove("buttonOnMouse");
        }
        if (event.getSource() == ThreeBotButton) {
            ThreeBotButton.getStyleClass().remove("buttonOnMouse");
        }
    }

    @FXML
    void OnActionExitButton(ActionEvent event) throws IOException {
        MainMenuView.getInstance().close();
    }



}