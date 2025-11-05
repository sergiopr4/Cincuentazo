package com.example.cincuentazo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class BotsController {

    @FXML
    private Button OneBotButton;

    @FXML
    private Button ThreeBotButton;

    @FXML
    private Button TwoBotButton;

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

}