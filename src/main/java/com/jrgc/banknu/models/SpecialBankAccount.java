package com.jrgc.banknu.models;

import com.jrgc.banknu.exceptions.BalanceException;

public class SpecialBankAccount extends BankAccount {
    private double limit;

    public SpecialBankAccount() {
        super(AccountType.SPECIAL);
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public void withdraw(float amount) throws BalanceException {
        if (getBalance() >= amount)
            setBalance(getBalance() - amount);
        else if (limit >= amount - getBalance()) {
            limit -= amount - getBalance();
            setBalance(0);
        }else
            throw new BalanceException("Saldo e limite insuficientes");
    }
}
