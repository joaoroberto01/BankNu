package com.jrgc.banknu.models;

import com.jrgc.banknu.utils.Utils;

public class BankStatementItem {
    public enum BankOperation {
        DEPOSIT, WITHDRAW, TRANSFER;

        @Override
        public String toString() {
            return switch (this){
                case DEPOSIT -> "Depósito";
                case WITHDRAW -> "Saque";
                case TRANSFER -> "Transferência";
            };
        }
    }

    private final float amount;
    private final BankOperation operation;
    private final String dateTime;

    public BankStatementItem(float amount, BankOperation operation) {
        this.amount = amount;
        this.operation = operation;
        dateTime = Utils.getCurrentDateTime();
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
