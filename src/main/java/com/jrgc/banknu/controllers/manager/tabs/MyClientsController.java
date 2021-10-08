package com.jrgc.banknu.controllers.manager.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.manager.BankStatementController;
import com.jrgc.banknu.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyClientsController {

    @FXML
    public Accordion clientsAccordion;

    @FXML
    private Label emptyListText;

    private List<Client> clients;

    @FXML
    public void initialize(){
        System.out.println("MyClients initialize");

        onRefresh();
        checkListVisibility();
    }

    private void checkListVisibility() {
        boolean emptyAccounts = clients == null || clients.isEmpty();
        clientsAccordion.setManaged(!emptyAccounts);
        emptyListText.setManaged(emptyAccounts);
    }

    public void onRefresh() {
        clients = ((Manager) BankApplication.currentUser).getClients();
        checkListVisibility();

        if (clients == null)
            return;

        final List<TitledPane> titledPanes = new ArrayList<>();

        for (Client client : clients) {
            TitledPane titledPane = new TitledPane(client.getUsername(), getPaneContent(client.getBankAccounts()));
            titledPanes.add(titledPane);
        }

        clientsAccordion.getPanes().setAll(titledPanes);
    }

    private void bankStatement(BankAccount selectedAccount){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("manager/bankstatement-view.fxml"));
            Parent parent = fxmlLoader.load();
            BankStatementController bankStatementController = fxmlLoader.getController();
            bankStatementController.setupAccount(selectedAccount);

            Scene scene = new Scene(parent, 480, 480);
            Stage stage = new Stage();
            stage.setTitle(String.format("Extrato - Conta %d", selectedAccount.getNumber()));
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Node getPaneContent(List<BankAccount> bankAccounts){
        if (bankAccounts == null || bankAccounts.isEmpty()) {
            Label label = new Label("Nenhuma conta registrada para esse cliente");
            label.setPadding(new Insets(20));
            return label;
        }

        ListView<BankAccount> accountListView = new ListView<>();
        accountListView.getItems().setAll(bankAccounts);

        final ContextMenu menu = new ContextMenu();

        MenuItem bankStatementMenuItem = new MenuItem("Ver Extrato");
        bankStatementMenuItem.setOnAction(event -> bankStatement(accountListView.getSelectionModel().getSelectedItem()));
        menu.getItems().add(bankStatementMenuItem);

        accountListView.setContextMenu(menu);

        VBox vBox = new VBox(10);
        vBox.getChildren().add(new Label("Use o botão direito para ver o extrato"));
        vBox.getChildren().add(accountListView);

        return vBox;
    }
}