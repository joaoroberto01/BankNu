package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.*;
import com.jrgc.banknu.utils.EncryptUtils;
import com.jrgc.banknu.utils.SceneManager;
import com.jrgc.banknu.utils.UserPersistUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class NewUserController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorText;

    private BankUser.UserType userType;
    private BankUser newUser;

    public void setUserType(BankUser.UserType userType){
        this.userType = userType;
    }

    public BankUser getNewUser(){
        return newUser;
    }

    public void newUser(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isBlank() || password.isBlank()) {
            errorText.setText("Preencha todos os campos");
            return;
        }

        password = EncryptUtils.toSHA1(password);

        newUser = switch (userType) {
            case CLIENT -> new Client(username, password);
            case MANAGER -> new Manager(username, password);
        };

        if (newUser instanceof Client)
            ((Manager) BankApplication.currentUser).getClients().add((Client) newUser);

        BankApplication.bankUsers.add(newUser);

        UserPersistUtils.storeUsers();

        Stage currentStage = SceneManager.getCurrentStage((Node) actionEvent.getSource());
        currentStage.hide();
    }
}