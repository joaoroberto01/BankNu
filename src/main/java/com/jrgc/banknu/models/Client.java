package com.jrgc.banknu.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Client extends BankUser {
    private transient ObservableList<BankAccount> bankAccounts;

    public Client(String username, String password){
        super(username, password, UserType.CLIENT);
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public ObservableList<BankAccount> getBankAccounts() {
        if (bankAccounts == null)
            bankAccounts = FXCollections.observableArrayList();
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts){
        this.bankAccounts = FXCollections.observableList(bankAccounts);
    }
}
