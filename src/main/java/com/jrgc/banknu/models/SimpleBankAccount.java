package com.jrgc.banknu.models;

import com.jrgc.banknu.exceptions.BalanceException;

public class SimpleBankAccount extends BankAccount {

    public SimpleBankAccount() {
        super(AccountType.SIMPLE);
    }

    protected SimpleBankAccount(AccountType accountType){
        super(accountType);
    }

    @Override
    public void withdraw(float amount) throws BalanceException {
        if (getBalance() >= amount)
            setBalance(getBalance() - amount);
        else
            throw new BalanceException();
    }
}
