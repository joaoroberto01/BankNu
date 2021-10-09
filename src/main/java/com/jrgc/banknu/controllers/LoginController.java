package com.jrgc.banknu.controllers;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.BankUser;
import com.jrgc.banknu.utils.EncryptUtils;
import com.jrgc.banknu.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class LoginController {
    @FXML
    private Label errorText;

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onSignIn(ActionEvent actionEvent) {
        boolean success;
        String inputUsername = userField.getText();
        String inputPassword = EncryptUtils.toSHA1(passwordField.getText());

        for (BankUser bankUser : BankApplication.bankUsers){
            success = bankUser.auth(inputUsername, inputPassword);
            if (success){
                BankApplication.currentUser = bankUser;
                break;
            }
        }

        if (BankApplication.currentUser == null)
            errorText.setManaged(true);
        else{
            Stage currentStage = SceneManager.getCurrentStage((Node) actionEvent.getSource());
            SceneManager.goToMain(currentStage, BankApplication.currentUser.getUsertype());
        }

    }
}