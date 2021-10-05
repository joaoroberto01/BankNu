package com.jrgc.banknu.models;

import com.jrgc.banknu.utils.Utils;

public class BankStatementItem {
    public enum BankOperation {
        WITHDRAW, DEPOSIT;

        @Override
        public String toString() {
            return switch (this){
                case DEPOSIT -> "Depósito";
                case WITHDRAW -> "Saque";
            };
        }
    }

    private final int accountNumber;
    private final float amount;
    private final BankOperation operation;
    private final String dateTime;

    public BankStatementItem(int accountNumber, float amount, BankOperation operation) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.operation = operation;
        dateTime = Utils.getCurrentDateTime();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public float getAmount() {
        return amount;
    }

    public BankOperation getOperation() {
        return operation;
    }

    public String getDateTime() {
        return dateTime;
    }
}
