package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.HomeController;
import com.jrgc.banknu.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientHomeController extends HomeController {

    @FXML
    public void initialize(){
        welcomeText.setText(String.format("%s, %s,\nSeja bem-vindo ao roxinho!", Utils.getGreeting(), BankApplication.currentUser.getUsername()));
    }
}