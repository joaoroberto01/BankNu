package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.BankStatementController;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ClientBankStatementController extends BankStatementController {

    @FXML
    public ChoiceBox<BankAccount> accountsChoiceBox;

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("Client BankStatement initialize");
    }

    public void onRefresh() {
        final Client currentClient = (Client) BankApplication.currentUser;

        accountsChoiceBox.getItems().setAll(currentClient.getBankAccounts());

        if (accountsChoiceBox.getItems().size() != 0) {
            int index = accountsChoiceBox.getItems().indexOf(selectedAccount);
            index = index == -1 ? 0 : index;
            accountsChoiceBox.getSelectionModel().select(index);
        }
        updateTable();
    }

    public void onSelectChoice() {
        if (accountsChoiceBox.getValue() == null)
            return;

        selectedAccount = accountsChoiceBox.getValue();

        updateTable();
    }
}