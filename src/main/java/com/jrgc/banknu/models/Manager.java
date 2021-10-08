package com.jrgc.banknu.models;

import java.util.ArrayList;
import java.util.List;

public class Manager extends BankUser {
    private final List<Client> clients = new ArrayList<>();

    public Manager(String username, String password){
        super(username, password, UserType.MANAGER);
    }

    public List<Client> getClients(){
        return clients;
    }
}
