package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize(){
        welcomeText.setText(Utils.getGreeting() + ", Cliente\nSeja bem-vindo ao roxinho!");
    }

    @FXML
    protected void onPasswordClick(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("client/change-password-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 250);

            Stage stage = new Stage();
            stage.setTitle("Alterar Senha");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogoutClick(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        BankApplication.goToLoginScene(stage);
    }
}