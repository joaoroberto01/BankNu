package com.jrgc.banknu.models;

import com.jrgc.banknu.exceptions.BalanceException;
import com.jrgc.banknu.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BankAccount {

    public enum AccountType {
        SIMPLE, SPECIAL, SAVINGS;

        @Override
        public String toString() {
            return switch (this) {
                case SIMPLE -> "Conta Simples";
                case SPECIAL -> "Conta Especial";
                case SAVINGS -> "Conta Poupança";
            };
        }
    }

    private final int number;
    private float balance;
    private final AccountType accountType;
    private final List<BankStatementItem> bankStatement = new ArrayList<>();

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void deposit(float amount){
        balance += amount;
    }

    public void transfer(BankAccount destinationAccount, float amount) throws BalanceException {
        withdraw(amount);
        destinationAccount.deposit(amount);
    }

    public List<BankStatementItem> getBankStatement() {
        return bankStatement;
    }

    @Override
    public String toString() {
        return String.format("%s - %s\nSaldo: %s", accountType, number, Utils.getCurrencyFormatted(balance));
    }

    public abstract void withdraw(float amount) throws BalanceException;
}
