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
            accountsChoiceBox.getItems().set(index, bankAccount);
            accountsChoiceBox.getSelectionModel().select(index);

            AlertUtils.showInformation("Saque efetuado com sucesso!");
        } catch (BalanceException balanceException) {
            AlertUtils.showError(balanceException.getMessage());
        }catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }

    public void onRefresh() {
        final Client currentClient = (Client) BankApplication.currentUser;
        accountsChoiceBox.getItems().setAll(currentClient.getBankAccounts());

        moneyTextField.setAmount(0.0);
    }
}