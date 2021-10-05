package com.jrgc.banknu.controllers;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.BankUser;
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
    protected void onSignIn(ActionEvent event) {
        boolean success;
        String inputUsername = EncryptUtils.toSHA1(userField.getText());
        String inputPassword = EncryptUtils.toSHA1(passwordField.getText());

        try {
            BufferedReader br = new BufferedReader(new FileReader("auth.bn"));

            String line, read = "";
            while ((line = br.readLine()) != null)
                read = read.concat(line);

            br.close();

            byte[] decoded = Base64.getDecoder().decode(read);
            String json = new String(decoded, StandardCharsets.UTF_8);

            List<BankUser> bankUsers = BankUser.listFrom(json);

            for (BankUser bankUser : bankUsers){
                System.out.println(bankUser.getUsername());
                System.out.println(bankUser.getPassword());
                System.out.println(bankUser.getUsertype());

                success = bankUser.auth(inputUsername, inputPassword);
                if (success){
                    BankApplication.currentUser = bankUser;
                    break;
                }
            }

            for (BankUser bankUser : bankUsers)
                if (!BankApplication.bankUsers.contains(bankUser))
                    BankApplication.bankUsers.add(bankUser);
        } catch (IOException e){
            e.printStackTrace();
        }

        if (BankApplication.currentUser == null)
            errorText.setText("Email ou senha inválidos");
        else{
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            BankUser.UserType userType = BankApplication.currentUser.getUsertype();

            String view = switch (userType){
                case CLIENT -> "client/client-view.fxml";
                case MANAGER -> "";
            };

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(view));
                Scene scene = new Scene(fxmlLoader.load(), 480, 480);

                stage.setScene(scene);
                stage.setTitle("Acesso " + userType);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}