package com.jrgc.banknu;

import com.jrgc.banknu.models.BankUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankApplication extends javafx.application.Application {

    public static List<BankUser> bankUsers = new ArrayList<>();
    public static BankUser currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        goToLoginScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void goToLoginScene(Stage stage){
        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("client/client-view.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 480, 480);
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);

            stage.setTitle("Autenticação BankNu");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}