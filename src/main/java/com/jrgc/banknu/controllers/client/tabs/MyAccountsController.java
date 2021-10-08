package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.client.ClientController;
import com.jrgc.banknu.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class MyAccountsController {
    private static final int SIMPLE_MAX = 1;
    private static final int SAVINGS_MAX = 2;
    private static final int SPECIAL_MAX = 3;

    @FXML
    public ListView<BankAccount> accountsListView;

    @FXML
    private MenuItem menuSimple, menuSpecial, menuSavings;

    @FXML
    private Label emptyListText;

    private int simpleCount, specialCount, savingsCount;

    @FXML
    public void initialize(){
        System.out.println("MyAccount initialize");

        //accountsListView.setSelectionModel(null);
        checkListVisibility();
    }

    private void checkListVisibility() {
        boolean emptyAccounts = accountsListView.getItems().isEmpty();
        accountsListView.setManaged(!emptyAccounts);
        emptyListText.setManaged(emptyAccounts);
    }

    public void onRefresh() {
        accountsListView.getItems().setAll(((Client) BankApplication.currentUser).getBankAccounts());
        checkListVisibility();
    }

    @FXML
    public void onSimpleAccountClick(){
        openNewAccount(BankAccount.AccountType.SIMPLE);
        simpleCount++;

        if (simpleCount == SIMPLE_MAX)
            menuSimple.setDisable(true);
    }

    @FXML
    public void onSpecialAccountClick(){
        openNewAccount(BankAccount.AccountType.SPECIAL);
        specialCount++;

        if (specialCount == SPECIAL_MAX)
            menuSpecial.setDisable(true);
    }

    @FXML
    public void onSavingsAccountClick(){
        openNewAccount(BankAccount.AccountType.SAVINGS);
        savingsCount++;

        if (savingsCount == SAVINGS_MAX)
            menuSavings.setDisable(true);
    }

    private void openNewAccount(BankAccount.AccountType accountType){
        BankAccount account = switch (accountType) {
            case SIMPLE -> new SimpleBankAccount();
            case SPECIAL -> new SpecialBankAccount();
            case SAVINGS -> new SavingsBankAccount();
        };

        ((Client) BankApplication.currentUser).getBankAccounts().add(account);
        accountsListView.getItems().add(account);
        checkListVisibility();
    }
}