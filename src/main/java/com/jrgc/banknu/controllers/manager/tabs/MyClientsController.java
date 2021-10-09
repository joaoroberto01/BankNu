package com.jrgc.banknu.controllers.manager.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.BankStatementController;
import com.jrgc.banknu.controllers.DepositController;
import com.jrgc.banknu.models.*;
import com.jrgc.banknu.utils.SceneManager;
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

    private Node getPaneContent(List<BankAccount> bankAccounts){
        if (bankAccounts == null || bankAccounts.isEmpty()) {
            Label label = new Label("Nenhuma conta registrada para esse cliente");
            label.setPadding(new Insets(20));
            return label;
        }

        final ListView<BankAccount> accountListView = new ListView<>();
        accountListView.getItems().setAll(bankAccounts);

        final ContextMenu menu = new ContextMenu();

        MenuItem bankStatementMenuItem = new MenuItem("Ver Extrato");
        bankStatementMenuItem.setOnAction(event -> SceneManager.popUpBankStatement(accountListView.getSelectionModel().getSelectedItem()));

        MenuItem depositMenuItem = new MenuItem("Depósito");
        depositMenuItem.setOnAction(event -> {
            SceneManager.popUpDeposit(accountListView.getSelectionModel().getSelectedItem());
            accountListView.refresh();
        });

        MenuItem transferMenuItem = new MenuItem("Transferir");
        transferMenuItem.setOnAction(event -> {
            SceneManager.popUpTransfer(accountListView.getSelectionModel().getSelectedItem());
            accountListView.refresh();
        });

        menu.getItems().addAll(bankStatementMenuItem, depositMenuItem, transferMenuItem);

        accountListView.setContextMenu(menu);

        VBox vBox = new VBox(10);
        vBox.getChildren().add(new Label("Use o botão direito para ver as opções da conta"));
        vBox.getChildren().add(accountListView);

        return vBox;
    }
}