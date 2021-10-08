package com.jrgc.banknu.models;

import com.jrgc.banknu.exceptions.BalanceException;

public class SpecialBankAccount extends BankAccount {
    private float limit;

    public SpecialBankAccount() {
        super(AccountType.SPECIAL);
    }

    public void withdraw(float amount) throws BalanceException {
        if (getBalance() >= amount)
            setBalance(getBalance() - amount);
        else if (limit >= amount)
            limit -= amount;
        else
            throw new BalanceException("Saldo e limite insuficientes");
    }
}
