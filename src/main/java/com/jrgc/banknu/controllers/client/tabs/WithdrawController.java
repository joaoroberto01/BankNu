package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.exceptions.BalanceException;
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
            int index = accountsChoiceBox.getSelectionModel().getSelectedIndex();
            //String warning = index == -1 ? "Selecione uma conta" : "";

            ClientController.bankAccounts.get(index).withdraw(moneyTextField.getAmount().floatValue());

            AlertUtils.showInformation("Saque efetuado com sucesso!");
        } catch (BalanceException balanceException) {
            AlertUtils.showError(balanceException.getMessage());
        }catch (Exception exception){
            AlertUtils.showWarning("Selecione uma conta");
        }
    }

    public void updateAccounts() {
        for (BankAccount b : ClientController.bankAccounts)
            if (!accountsChoiceBox.getItems().contains(b))
                accountsChoiceBox.getItems().add(b);
    }
}