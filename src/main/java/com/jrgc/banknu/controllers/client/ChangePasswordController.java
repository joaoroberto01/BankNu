package com.jrgc.banknu.controllers.client;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.utils.EncryptUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class ChangePasswordController {

    @FXML
    public PasswordField oldPasswordField, newPasswordField;

    @FXML
    public Label errorText;

    @FXML
    public void initialize(){
        System.out.println("Password initialize");
    }

    @FXML
    protected void onChangePassword(ActionEvent actionEvent){
        String oldPassword = EncryptUtils.toSHA1(oldPasswordField.getText());
        String newPassword = EncryptUtils.toSHA1(newPasswordField.getText());
        if (!oldPassword.equals(newPassword)) {
            errorText.setText("⚠ As senhas não coincidem");
            errorText.setManaged(true);
            return;
        }
        boolean success = BankApplication.currentUser.changePassword(oldPassword, newPassword);
        errorText.setManaged(!success);
        if (!success) {
            errorText.setText("⚠ Senha incorreta.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Senha alterada com sucesso!");
        alert.showAndWait();

        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}