package com.jrgc.banknu.controllers;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.utils.EncryptUtils;
import com.jrgc.banknu.utils.SceneManager;
import com.jrgc.banknu.utils.UserPersistUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML
    public PasswordField oldPasswordField, newPasswordField;

    @FXML
    public Label errorText;

    @FXML
    protected void onChangePassword(ActionEvent actionEvent){
        String oldPassword = EncryptUtils.toSHA1(oldPasswordField.getText());
        String newPassword = EncryptUtils.toSHA1(newPasswordField.getText());

        boolean success = BankApplication.currentUser.changePassword(oldPassword, newPassword);
        errorText.setManaged(!success);
        if (!success) {
            errorText.setText("⚠ Senha incorreta.");
            return;
        }
        //Set new password
        errorText.setText("Senha alterada com sucesso");
        UserPersistUtils.storeUsers();

        Stage currentStage = SceneManager.getCurrentStage((Node) actionEvent.getSource());
        currentStage.hide();
    }
}