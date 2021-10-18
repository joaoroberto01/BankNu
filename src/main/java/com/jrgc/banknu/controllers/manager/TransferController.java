package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.models.Manager;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.List;

public class TransferController {

    @FXML
    public CurrencyField moneyTextField;

    @FXML
    public Label accountText;

    @FXML
    public ChoiceBox<Client> clientChoiceBox;

    @FXML
    public ChoiceBox<BankAccount> destinationAccountChoiceBox;

    private BankAccount sourceAccount;

    public void setupSourceAccount(Client sourceClient, BankAccount sourceAccount) {
        this.sourceAccount = sourceAccount;

        accountText.setText(String.format("Transferência entre contas\nConta %d", sourceAccount.getNumber()));
        clientChoiceBox.getItems().remove(sourceClient);
    }

    public void initialize() {
        List<Client> clients = ((Manager) BankApplication.currentUser).getClients();
        clientChoiceBox.getItems().setAll(clients);
    }

    public void onClientSelect() {
        destinationAccountChoiceBox.setManaged(true);
        destinationAccountChoiceBox.getItems().setAll(clientChoiceBox.getValue().getBankAccounts());
    }

    @FXML
    public void onTransferClick() {
        try {
            float amount = moneyTextField.getAmount().floatValue();

            if (destinationAccountChoiceBox.getValue() == null) {
                AlertUtils.showWarning("Selecione uma conta");
                return;
            }

            if (amount == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            BankAccount destinationAccount = destinationAccountChoiceBox.getValue();

            sourceAccount.transfer(destinationAccount, amount);

            BankStatementItem sourceItem = new BankStatementItem(-amount, BankStatementItem.BankOperation.TRANSFER);
            sourceAccount.getBankStatement().add(sourceItem);

            BankStatementItem destinationItem = new BankStatementItem(amount, BankStatementItem.BankOperation.TRANSFER);
            destinationAccount.getBankStatement().add(destinationItem);


            AlertUtils.showInformation("Transferência efetuada com sucesso!");
        } catch (BalanceException balanceException) {
            AlertUtils.showError(balanceException.getMessage());
        }
    }
}