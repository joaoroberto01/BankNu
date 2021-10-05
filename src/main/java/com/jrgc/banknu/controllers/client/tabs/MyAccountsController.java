package com.jrgc.banknu.controllers.client.tabs;

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

        accountsListView.setMouseTransparent(true);
        accountsListView.setFocusTraversable(false);
        checkListVisibility();
    }

    private void checkListVisibility() {
        boolean emptyAccounts = accountsListView.getItems().size() == 0;
        accountsListView.setManaged(!emptyAccounts);
        emptyListText.setManaged(emptyAccounts);
    }

    public void onRefresh() {
        accountsListView.getItems().setAll(ClientController.bankAccounts);
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
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("client/new-account-view.fxml"));
//            NewAccountController newAccountController = new NewAccountController(accountType, accountsListView);
//            fxmlLoader.setController(newAccountController);
//            Scene scene = new Scene(fxmlLoader.load(), 320, 250);
//
//            Stage stage = new Stage();
//            stage.setTitle("Nova Conta");
//            stage.setScene(scene);
//            stage.show();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        BankAccount account = switch (accountType) {
            case SIMPLE -> new SimpleBankAccount();
            case SPECIAL -> new SpecialBankAccount();
            case SAVINGS -> new SavingsBankAccount();
        };

        ClientController.bankAccounts.add(account);
        accountsListView.getItems().add(account);
        checkListVisibility();
    }
}