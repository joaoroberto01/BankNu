package com.jrgc.banknu.controllers.client;

import com.jrgc.banknu.models.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class NewAccountController {
    private BankAccount.AccountType accountType;
    private ListView<BankAccount> listView;

    @FXML
    private Button btnFinish;

    public NewAccountController(BankAccount.AccountType accountType, ListView<BankAccount> listView) {
        this.listView = listView;
        this.accountType = accountType;
    }

    @FXML
    public void initialize() {
        btnFinish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newAccount();
            }
        });
    }

    private void newAccount() {
        BankAccount account = switch (accountType) {
            case SIMPLE -> new SimpleBankAccount();
            case SPECIAL -> new SpecialBankAccount();
            case SAVINGS -> new SavingsBankAccount();
        };

        listView.getItems().add(account);
        listView.setVisible(true);
        listView.refresh();
    }
}