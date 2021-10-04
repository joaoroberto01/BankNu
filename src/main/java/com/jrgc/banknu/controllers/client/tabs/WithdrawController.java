package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.controllers.client.ClientController;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

public class WithdrawController {
    @FXML
    private ChoiceBox<BankAccount> accountsChoiceBox;

    @FXML
    private CurrencyField moneyTextField;

    @FXML
    public void initialize() {
        System.out.println("Withdraw initialize");
    }

    @FXML
    public void onWithdrawClick(){
        try {
            float amount = moneyTextField.getAmount().floatValue();

            if (amount == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();

            BankAccount bankAccount = ClientController.bankAccounts.get(index);
            bankAccount.withdraw(amount);

            BankStatementItem item = new BankStatementItem(bankAccount.getNumber(), amount, BankStatementItem.BankOperation.WITHDRAW);
            ClientController.bankStatement.add(item);

            accountsChoiceBox.getItems().set(index, ClientController.bankAccounts.get(index));
            accountsChoiceBox.getSelectionModel().select(index);

            AlertUtils.showInformation("Saque efetuado com sucesso!");
        } catch (BalanceException balanceException) {
            AlertUtils.showError(balanceException.getMessage());
        }catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }

    public void onRefresh() {
        accountsChoiceBox.getItems().setAll(ClientController.bankAccounts);
        accountsChoiceBox.getSelectionModel().selectFirst();

        moneyTextField.setAmount(0.0);
    }
}