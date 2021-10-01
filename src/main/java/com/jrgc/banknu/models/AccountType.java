package com.jrgc.banknu.models;

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
