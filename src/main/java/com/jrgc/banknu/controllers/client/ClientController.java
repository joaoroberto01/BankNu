package com.jrgc.banknu.controllers.client;

import com.jrgc.banknu.controllers.client.tabs.DepositController;
import com.jrgc.banknu.controllers.client.tabs.WithdrawController;
import com.jrgc.banknu.models.BankAccount;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class ClientController implements InvalidationListener {
    public static ObservableList<BankAccount> bankAccounts = FXCollections.observableArrayList();

    @FXML
    public WithdrawController withdrawController;

    @FXML
    public DepositController depositController;

    @FXML
    public TabPane tabPane;

    @FXML
    protected void initialize(){
        tabPane.getSelectionModel().select(0);

        bankAccounts.addListener(this);
        setTabsEnabled();
    }

    @FXML
    public void refresh(){
        System.out.println("refreshed");
        withdrawController.updateAccounts();
        depositController.updateAccounts();
    }

    public void setTabsEnabled(){
        boolean emptyList = bankAccounts.isEmpty();
        for (int i = 2; i < tabPane.getTabs().size(); i++)
            tabPane.getTabs().get(i).setDisable(emptyList);
    }

    //Accounts List changed
    @Override
    public void invalidated(Observable observable) {
        setTabsEnabled();
    }
}