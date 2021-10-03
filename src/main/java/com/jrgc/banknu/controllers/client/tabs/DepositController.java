package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.controllers.client.ClientController;
import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

public class DepositController {

    @FXML
    public ChoiceBox<BankAccount> accountsChoiceBox;

    @FXML
    public CurrencyField moneyTextField;

    @FXML
    public void initialize(){
        System.out.println("Deposit initialize");
    }

    @FXML
    public void onDepositClick(){
        int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();
        ClientController.bankAccounts.get(index).deposit(moneyTextField.getAmount().floatValue());

        AlertUtils.showInformation("Depósito efetuado com sucesso!");
    }

    public void updateAccounts() {
        for (BankAccount b : ClientController.bankAccounts)
            if (!accountsChoiceBox.getItems().contains(b))
                accountsChoiceBox.getItems().add(b);
    }
}