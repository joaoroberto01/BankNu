package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.*;
import com.jrgc.banknu.utils.EncryptUtils;
import com.jrgc.banknu.utils.UserPersist;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

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

        if (newUser instanceof Client) {
            Manager currentUser = (Manager) BankApplication.currentUser;
            currentUser.getClients().add((Client) newUser);

            System.out.println(currentUser.getClients());
            int i = BankApplication.bankUsers.indexOf(currentUser);
            System.out.println(i);
            BankApplication.bankUsers.set(i, currentUser);
        }

        BankApplication.bankUsers.add(newUser);

        UserPersist.storeUsers();

        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}