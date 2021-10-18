package com.jrgc.banknu.utils;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.BankStatementController;
import com.jrgc.banknu.controllers.DepositController;
import com.jrgc.banknu.controllers.WithdrawController;
import com.jrgc.banknu.controllers.manager.AccountDetailsController;
import com.jrgc.banknu.controllers.manager.NewUserController;
import com.jrgc.banknu.controllers.manager.TransferController;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankUser;
import com.jrgc.banknu.models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SceneManager {
    private static final int DEFAULT_WINDOW_WIDTH = 480;
    private static final int DEFAULT_WINDOW_HEIGHT = 480;

    private static class SceneDetails {
        public final String path;
        public final String title;
        public int width;
        public int height;

        public SceneDetails(String path, String title, int width, int height) {
            this.path = path;
            this.title = title;
            this.width = width;
            this.height = height;
        }

        public SceneDetails(String path, String title) {
            this(path, title, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        }
    }

    public static Stage getCurrentStage(Node node){
        return (Stage) node.getScene().getWindow();
    }

    public static void goToLogin(Stage stage){
        SceneDetails sceneDetails = new SceneDetails("login-view.fxml", "Autenticação BankNu", 380, 320);

        showScene(stage, sceneDetails);
    }

    public static void goToMain(Stage stage, BankUser.UserType userType){
        String path = switch (userType){
            case CLIENT -> "client/client-view.fxml";
            case MANAGER -> "manager/manager-view.fxml";
        };

        SceneDetails sceneDetails = new SceneDetails(path, "Acesso " + userType);
        showScene(stage, sceneDetails);
    }

    public static void popUpChangePassword(){
        SceneDetails sceneDetails = new SceneDetails("change-password-view.fxml", "Alterar Senha", 320, 280);
        showScene(new Stage(), sceneDetails);
    }

    public static BankUser popUpNewUser(BankUser.UserType userType){
        SceneDetails sceneDetails = new SceneDetails("manager/tabs/new-user-view.fxml", "Novo Usuário", 320, 240);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));
            Parent parent = fxmlLoader.load();
            NewUserController newUserController = fxmlLoader.getController();
            newUserController.setUserType(userType);

            showPopUpStage(parent, sceneDetails);

            return newUserController.getNewUser();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void popUpBankStatement(BankAccount selectedAccount){
        String title = String.format("Extrato - Conta %d", selectedAccount.getNumber());
        SceneDetails sceneDetails = new SceneDetails("manager/bankstatement-view.fxml", title);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));
            Parent parent = fxmlLoader.load();
            BankStatementController bankStatementController = fxmlLoader.getController();
            bankStatementController.setupAccount(selectedAccount);

            showPopUpStage(parent, sceneDetails);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void popUpDeposit(BankAccount selectedAccount) {
        String title = String.format("Depósito - Conta %d", selectedAccount.getNumber());
        SceneDetails sceneDetails = new SceneDetails("manager/deposit-view.fxml", title, 300, 240);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));
            Parent parent = fxmlLoader.load();
            DepositController depositController = fxmlLoader.getController();
            depositController.setupAccount(selectedAccount);

            showPopUpStage(parent, sceneDetails);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void popUpWithdraw(BankAccount selectedAccount) {
        String title = String.format("Extrato - Conta %d", selectedAccount.getNumber());
        SceneDetails sceneDetails = new SceneDetails("manager/withdraw-view.fxml", title, 300, 240);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));
            Parent parent = fxmlLoader.load();
            WithdrawController withdrawController = fxmlLoader.getController();
            withdrawController.setupAccount(selectedAccount);

            showPopUpStage(parent, sceneDetails);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void popUpTransfer(Client sourceClient, BankAccount sourceAccount) {
        SceneDetails sceneDetails = new SceneDetails("manager/transfer-view.fxml", "Transferência entre contas", 300, 350);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));
            Parent parent = fxmlLoader.load();
            TransferController transferController = fxmlLoader.getController();
            transferController.setupSourceAccount(sourceClient, sourceAccount);

            showPopUpStage(parent, sceneDetails);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void popUpAccountDetails(BankAccount selectedAccount) {
        String title = "Detalhes da " + selectedAccount.getAccountType();

        SceneDetails sceneDetails = new SceneDetails("manager/account-details-view.fxml", title, 320, 240);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));
            Parent parent = fxmlLoader.load();
            AccountDetailsController accountDetailsController = fxmlLoader.getController();
            accountDetailsController.setupAccount(selectedAccount);

            showPopUpStage(parent, sceneDetails);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void showScene(Stage stage, SceneDetails sceneDetails){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource(sceneDetails.path));

            Scene scene = new Scene(fxmlLoader.load(), sceneDetails.width, sceneDetails.height);

            stage.setTitle(sceneDetails.title);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void showPopUpStage(Parent parent, SceneDetails sceneDetails){
        Scene scene = new Scene(parent, sceneDetails.width, sceneDetails.height);

        Stage stage = new Stage();
        stage.setTitle(sceneDetails.title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
