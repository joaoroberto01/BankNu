package com.jrgc.banknu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client/client-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 480, 480);

        stage.setTitle("Autenticação BankNu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}