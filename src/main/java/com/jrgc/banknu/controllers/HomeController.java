package com.jrgc.banknu.controllers;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    protected Label welcomeText;

    @FXML
    protected void onPasswordClick(){
        SceneManager.popUpChangePassword();
    }

    @FXML
    protected void onLogoutClick(ActionEvent actionEvent){
        BankApplication.currentUser = null;

        Stage stage = SceneManager.getCurrentStage((Node) actionEvent.getSource());
        SceneManager.goToLogin(stage);
    }
}