package com.jrgc.banknu.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Client extends BankUser {
    public static final int SIMPLE_MAX = 1;
    public static final int SAVINGS_MAX = 2;
    public static final int SPECIAL_MAX = 3;

    private transient ObservableList<BankAccount> bankAccounts;
    private transient int simpleCount, specialCount, savingsCount;

    public Client(String username, String password){
        super(username, password, UserType.CLIENT);
    }

    public ObservableList<BankAccount> getBankAccounts() {
        if (bankAccounts == null)
            bankAccounts = FXCollections.observableArrayList();
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts){
        this.bankAccounts = FXCollections.observableList(bankAccounts);
    }

    public int getSimpleCount() {
        return simpleCount;
    }

    public void incrementSimpleCount(){
        simpleCount++;
    }

    public int getSpecialCount() {
        return specialCount;
    }

    public void incrementSpecialCount(){
        specialCount++;
    }

    public int getSavingsCount() {
        return savingsCount;
    }

    public void incrementSavingsCount(){
        savingsCount++;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
