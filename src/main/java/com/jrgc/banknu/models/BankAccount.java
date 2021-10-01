package com.jrgc.banknu.models;

import java.util.Random;

public abstract class BankAccount {
    private final int number;
    private final AccountType accountType;

    public BankAccount(AccountType accountType) {
        this.number = new Random().nextInt(99999);
        this.accountType = accountType;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return accountType + " - " + number;
    }
}
