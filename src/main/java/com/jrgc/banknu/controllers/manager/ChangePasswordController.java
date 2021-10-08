package com.jrgc.banknu.controllers.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

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
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        if (!oldPassword.equals(newPassword)) {
            errorText.setText("As senhas não coincidem");
            return;
        }
        //Set new password
        errorText.setText("Senha alterada com sucesso");

        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}