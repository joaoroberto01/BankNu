package com.jrgc.banknu.controllers.manager.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.models.Manager;
import com.jrgc.banknu.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
            TitledPane titledPane = new TitledPane(client.getUsername(), getPaneContent(client));
            titledPanes.add(titledPane);
        }

        clientsAccordion.getPanes().setAll(titledPanes);
    }

    private Node getPaneContent(Client client){
        if (client.getBankAccounts() == null || client.getBankAccounts().isEmpty()) {
            Label label = new Label("Nenhuma conta registrada para esse cliente");
            label.setPadding(new Insets(20));
            return label;
        }

        ListView<BankAccount> accountListView = new ListView<>();

        accountListView.setCellFactory(listView -> {
            ListCell<BankAccount> listCell = new ListCell<>(){
                @Override
                protected void updateItem(BankAccount item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? null : item.toString());
                }
            };

            listCell.emptyProperty().addListener((observable, wasEmpty, isNowEmpty) -> {
                listCell.setContextMenu(isNowEmpty ? null : getContextMenu(client, listCell));
            });

            return listCell;
        });


        accountListView.getItems().setAll(client.getBankAccounts());

        Text text = new Text("Use o botão direito para ver as opções da conta");
        text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, 14));

        VBox vBox = new VBox(10);
        vBox.getChildren().add(text);
        vBox.getChildren().add(accountListView);

        return vBox;
    }

    private ContextMenu getContextMenu(Client client, ListCell<BankAccount> listCell){
        final ContextMenu menu = new ContextMenu();

        BankAccount selectedAccount = listCell.getItem();

        MenuItem bankStatementMenuItem = new MenuItem("Ver Extrato");
        bankStatementMenuItem.setOnAction(event -> SceneManager.popUpBankStatement(selectedAccount));

        MenuItem withdrawMenuItem = new MenuItem("Saque");
        withdrawMenuItem.setOnAction(event -> {
            SceneManager.popUpWithdraw(selectedAccount);
            listCell.getListView().refresh();
        });

        MenuItem depositMenuItem = new MenuItem("Depósito");
        depositMenuItem.setOnAction(event -> {
            SceneManager.popUpDeposit(selectedAccount);
            listCell.getListView().refresh();
        });

        MenuItem transferMenuItem = new MenuItem("Transferir");
        transferMenuItem.setOnAction(event -> {
            SceneManager.popUpTransfer(client, selectedAccount);
            listCell.getListView().refresh();
        });
        transferMenuItem.setDisable(clients.size() < 2);

        menu.getItems().addAll(bankStatementMenuItem, depositMenuItem, withdrawMenuItem, transferMenuItem);

        if (selectedAccount.getAccountType() != BankAccount.AccountType.SIMPLE) {
            String itemTitle = "";

            switch (selectedAccount.getAccountType()){
                case SPECIAL -> itemTitle = "Definir Limite Negativo";
                case SAVINGS -> itemTitle = "Definir Taxa da Poupança";
            };

            MenuItem customMenuItem = new MenuItem(itemTitle);
            customMenuItem.setOnAction(event -> {
                SceneManager.popUpAccountDetails(selectedAccount);
            });
            menu.getItems().add(customMenuItem);
        }

        return menu;
    }
}