package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class DepositController {

    @FXML
    public ChoiceBox<BankAccount> accountsChoiceBox;

    @FXML
    public CurrencyField moneyTextField;

    private Client currentClient;

    @FXML
    public void initialize(){
        System.out.println("Deposit initialize");
        currentClient = (Client) BankApplication.currentUser;
    }

    @FXML
    public void onDepositClick(){
        try {
            float amount = moneyTextField.getAmount().floatValue();

            if (amount == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            BankAccount bankAccount = accountsChoiceBox.getSelectionModel().getSelectedItem();
            bankAccount.deposit(amount);

            BankStatementItem item = new BankStatementItem(bankAccount.getNumber(), amount, BankStatementItem.BankOperation.DEPOSIT);
            bankAccount.getBankStatement().add(item);

            int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();
            accountsChoiceBox.getItems().set(index, bankAccount);
            accountsChoiceBox.getSelectionModel().select(index);

            AlertUtils.showInformation("Depósito efetuado com sucesso!");
        } catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }

    public void onRefresh() {
        accountsChoiceBox.getItems().setAll(currentClient.getBankAccounts());

        moneyTextField.setAmount(0.0);
    }
}