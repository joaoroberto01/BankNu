package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class WithdrawController {
    @FXML
    private ChoiceBox<BankAccount> accountsChoiceBox;

    @FXML
    private CurrencyField moneyTextField;

    private Client currentClient;

    @FXML
    public void initialize() {
        System.out.println("Withdraw initialize");
        currentClient = (Client) BankApplication.currentUser;
    }

    @FXML
    public void onWithdrawClick(){
        try {
            float amount = moneyTextField.getAmount().floatValue();

            if (amount == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            BankAccount bankAccount = accountsChoiceBox.getSelectionModel().getSelectedItem();
            bankAccount.withdraw(amount);

            BankStatementItem item = new BankStatementItem(bankAccount.getNumber(), amount, BankStatementItem.BankOperation.WITHDRAW);
            bankAccount.getBankStatement().add(item);

            int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();
            accountsChoiceBox.getItems().set(index, currentClient.getBankAccounts().get(index));
            accountsChoiceBox.getSelectionModel().select(index);

            AlertUtils.showInformation("Saque efetuado com sucesso!");
        } catch (BalanceException balanceException) {
            AlertUtils.showError(balanceException.getMessage());
        }catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }

    public void onRefresh() {
        accountsChoiceBox.getItems().setAll(currentClient.getBankAccounts());
        accountsChoiceBox.getSelectionModel().selectFirst();

        moneyTextField.setAmount(0.0);
    }
}