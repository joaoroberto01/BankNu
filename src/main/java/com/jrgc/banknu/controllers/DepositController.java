package com.jrgc.banknu.controllers;

import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DepositController {

    @FXML
    public CurrencyField moneyTextField;

    @FXML
    public Label accountText;

    protected BankAccount selectedAccount;

    public void setupAccount(BankAccount selectedAccount){
        this.selectedAccount = selectedAccount;
        accountText.setText(String.format("Depósito\nConta %d", selectedAccount.getNumber()));
    }

    @FXML
    public void onDepositClick(){
        float amount = moneyTextField.getAmount().floatValue();

        if (selectedAccount == null) {
            AlertUtils.showWarning("Selecione uma conta");
            return;
        }

        if (amount == 0) {
            AlertUtils.showWarning("Insira um valor maior que R$0,00");
            return;
        }

        selectedAccount.deposit(amount);

        BankStatementItem item = new BankStatementItem(amount, BankStatementItem.BankOperation.DEPOSIT);
        selectedAccount.getBankStatement().add(item);

        AlertUtils.showInformation("Depósito efetuado com sucesso!");
    }
}