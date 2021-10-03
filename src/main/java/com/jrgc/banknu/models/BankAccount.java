package com.jrgc.banknu.models;

import com.jrgc.banknu.exceptions.BalanceException;

import java.util.Random;

public abstract class BankAccount {
    private final int number;
    private float balance;
    private final AccountType accountType;

    public BankAccount(AccountType accountType) {
        this.number = new Random().nextInt(99999);
        this.balance = 0;
        this.accountType = accountType;
    }

    public int getNumber() {
        return number;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void deposit(float amount){
        balance += amount;
    }

    @Override
    public String toString() {
        return accountType + " - " + number;
    }

    public abstract void withdraw(float amount) throws BalanceException;
}
