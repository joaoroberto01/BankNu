package com.jrgc.banknu.models;

public class SavingsBankAccount extends SimpleBankAccount {

    private float tax;

    public SavingsBankAccount() {
        super(AccountType.SAVINGS);
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }
}
