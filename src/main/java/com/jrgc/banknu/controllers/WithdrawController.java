package com.jrgc.banknu.controllers;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WithdrawController {
    @FXML
    private CurrencyField moneyTextField;

    @FXML
    public Label accountText;

    protected BankAccount selectedAccount;

    public void setupAccount(BankAccount selectedAccount){
        this.selectedAccount = selectedAccount;
        accountText.setText(String.format("Saque\nConta %d", selectedAccount.getNumber()));
    }

    @FXML
    public void onWithdrawClick(){
        try {
            float amount = moneyTextField.getAmount().floatValue();

            if (amount == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            if (BankApplication.currentUser instanceof Client && amount > 110000) {
                AlertUtils.showError("Valor máximo de depósito excedido. Consulte seu gerente");
                return;
            }

            selectedAccount.withdraw(amount);

            BankStatementItem item = new BankStatementItem(amount, BankStatementItem.BankOperation.WITHDRAW);
            selectedAccount.getBankStatement().add(item);

            AlertUtils.showInformation("Saque efetuado com sucesso!");
        } catch (BalanceException balanceException) {
            AlertUtils.showError(balanceException.getMessage());
        }catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }
}