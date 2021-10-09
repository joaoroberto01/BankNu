package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.DepositController;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ClientDepositController extends DepositController {

    @FXML
    public ChoiceBox<BankAccount> accountsChoiceBox;

    @Override
    @FXML
    public void onDepositClick() {
        selectedAccount = accountsChoiceBox.getSelectionModel().getSelectedItem();
        super.onDepositClick();

        if (selectedAccount != null)
            onRefresh();
    }

    public void onRefresh() {
        int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();

        final Client currentClient = (Client) BankApplication.currentUser;
        accountsChoiceBox.getItems().setAll(currentClient.getBankAccounts());

        accountsChoiceBox.getSelectionModel().select(index);
        moneyTextField.setAmount(0.0);
    }
}