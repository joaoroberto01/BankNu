package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.utils.EncryptUtils;
import com.jrgc.banknu.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    protected void onSignInButtonClick(ActionEvent event) {
        boolean success = false;
        String email = null, password = null;
        String inputEmail = EncryptUtils.toSHA1(emailField.getText());
        String inputPassword = EncryptUtils.toSHA1(passwordField.getText());

        try {
            BufferedReader br = new BufferedReader(new FileReader("auth.txt"));

            email = br.readLine();
            password = br.readLine();

            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        if (email != null && password != null)
            success = inputEmail.equals(email) && inputPassword.equals(password);

        if (!success)
            welcomeText.setText("Email ou senha inválidos");
        else{
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("main/client-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 480, 480);

                stage.setScene(scene);
                stage.setTitle(Utils.getGreeting() + ", seja bem-vindo ao roxinho!");
            }catch (IOException e){
                e.printStackTrace();
                welcomeText.setText("deu ruim");
            }
        }

    }
}