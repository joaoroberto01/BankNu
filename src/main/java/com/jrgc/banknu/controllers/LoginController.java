package com.jrgc.banknu.controllers;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.utils.EncryptUtils;
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

public class LoginController {
    @FXML
    private Label errorText;

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onSignInButtonClick(ActionEvent event) {
        boolean success = false;
        String email = null, password = null;
        String inputEmail = EncryptUtils.toSHA1(userField.getText());
        String inputPassword = EncryptUtils.toSHA1(passwordField.getText());

        try {
            BufferedReader br = new BufferedReader(new FileReader("auth.bn"));

            email = br.readLine();
            password = br.readLine();

            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        if (email != null && password != null)
            success = inputEmail.equals(email) && inputPassword.equals(password);

        if (!success)
            errorText.setText("Email ou senha inválidos");
        else{
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("client/client-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 480, 480);

                stage.setScene(scene);
                stage.setTitle("Acesso Cliente");
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}