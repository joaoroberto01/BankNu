package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.controllers.client.ClientController;
import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.util.function.UnaryOperator;

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
        try {
            float amount = moneyTextField.getAmount().floatValue();

            if (amount == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();
            BankAccount bankAccount = ClientController.bankAccounts.get(index);
            bankAccount.deposit(amount);

            BankStatementItem item = new BankStatementItem(bankAccount.getNumber(), amount, BankStatementItem.BankOperation.DEPOSIT);
            ClientController.bankStatement.add(item);

            accountsChoiceBox.getItems().set(index, ClientController.bankAccounts.get(index));
            accountsChoiceBox.getSelectionModel().select(index);

            AlertUtils.showInformation("Depósito efetuado com sucesso!");
        } catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }

    public void onRefresh() {
        accountsChoiceBox.getItems().setAll(ClientController.bankAccounts);
        accountsChoiceBox.getSelectionModel().selectFirst();

        moneyTextField.setAmount(0.0);
    }
}