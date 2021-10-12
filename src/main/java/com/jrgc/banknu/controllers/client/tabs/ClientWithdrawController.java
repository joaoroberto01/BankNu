package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.WithdrawController;
import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ClientWithdrawController extends WithdrawController {
    @FXML
    private ChoiceBox<BankAccount> accountsChoiceBox;

    @FXML
    private CurrencyField moneyTextField;

    @Override
    public void onWithdrawClick() {
        selectedAccount = accountsChoiceBox.getSelectionModel().getSelectedItem();
        super.onWithdrawClick();

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