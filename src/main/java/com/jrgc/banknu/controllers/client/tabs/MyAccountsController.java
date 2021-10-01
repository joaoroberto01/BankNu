package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MyAccountsController {
    @FXML
    private ListView<BankAccount> accountsListView;

    @FXML
    private Label emptyListText;

    @FXML
    public void initialize(){
        System.out.println("MyAccount initialize");

        checkListVisibility();
    }

    private void checkListVisibility() {
        boolean emptyAccounts = accountsListView.getItems().size() == 0;
        accountsListView.setManaged(!emptyAccounts);
        emptyListText.setManaged(emptyAccounts);
    }

    @FXML
    public void onSimpleAccountClick(){
        openNewAccount(AccountType.SIMPLE);
    }

    @FXML
    public void onSpecialAccountClick(){
        openNewAccount(AccountType.SPECIAL);
    }

    @FXML
    public void onSavingsAccountClick(){
        openNewAccount(AccountType.SAVINGS);
    }

    private void openNewAccount(AccountType accountType){
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

        accountsListView.getItems().add(account);
        accountsListView.refresh();
        checkListVisibility();
    }
}