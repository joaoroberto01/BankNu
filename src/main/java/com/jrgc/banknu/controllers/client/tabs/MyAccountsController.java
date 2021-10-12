package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class MyAccountsController {
    @FXML
    public ListView<BankAccount> accountsListView;

    @FXML
    private MenuItem menuSimple, menuSpecial, menuSavings;

    @FXML
    private Label emptyListText;

    private Client currentClient;

    @FXML
    public void initialize(){
        System.out.println("MyAccount initialize");

        currentClient = (Client) BankApplication.currentUser;

        checkListVisibility();
    }

    private void checkListVisibility() {
        boolean emptyAccounts = accountsListView.getItems().isEmpty();
        accountsListView.setManaged(!emptyAccounts);
        emptyListText.setManaged(emptyAccounts);
    }

    private void checkMenuVisibility() {
        menuSimple.setDisable(currentClient.getSimpleCount() == Client.SIMPLE_MAX);
        menuSpecial.setDisable(currentClient.getSpecialCount() == Client.SPECIAL_MAX);
        menuSavings.setDisable(currentClient.getSavingsCount() == Client.SAVINGS_MAX);
    }

    public void onRefresh() {
        accountsListView.getItems().setAll(((Client) BankApplication.currentUser).getBankAccounts());
        checkListVisibility();
        checkMenuVisibility();
    }

    @FXML
    public void onSimpleAccountClick(){
        openNewAccount(BankAccount.AccountType.SIMPLE);
        currentClient.incrementSimpleCount();

        checkMenuVisibility();
    }

    @FXML
    public void onSpecialAccountClick(){
        openNewAccount(BankAccount.AccountType.SPECIAL);
        currentClient.incrementSpecialCount();

        checkMenuVisibility();
    }

    @FXML
    public void onSavingsAccountClick(){
        openNewAccount(BankAccount.AccountType.SAVINGS);
        currentClient.incrementSavingsCount();

        checkMenuVisibility();
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