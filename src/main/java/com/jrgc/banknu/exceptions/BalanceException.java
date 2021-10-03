package com.jrgc.banknu.exceptions;

public class BalanceException extends Exception {
    public BalanceException(){
        super("Saldo insuficiente");
    }

    public BalanceException(String message){
        super(message);
    }
}
