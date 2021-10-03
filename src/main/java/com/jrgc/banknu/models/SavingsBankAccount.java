package com.jrgc.banknu.models;

public class SavingsBankAccount extends SimpleBankAccount {

    private float tax;

    public SavingsBankAccount() {
        super(AccountType.SAVINGS);
    }
}
