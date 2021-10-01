package com.jrgc.banknu.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;

public class ClientController {

    @FXML
    public TabPane tabPane;

    @FXML
    protected void initialize(){
        tabPane.getSelectionModel().select(1);
    }
}