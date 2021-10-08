package com.jrgc.banknu.models;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public abstract class BankUser {
    private static final String CLIENT = "client";
    private static final String MANAGER = "manager";

    public enum UserType {
        @SerializedName("client")
        CLIENT,
        @SerializedName("manager")
        MANAGER;

        @Override
        public String toString() {
            return switch (this){
                case CLIENT -> "Cliente";
                case MANAGER -> "Gerente";
            };
        }
    }

    private final String username;
    private final String password;
    private final UserType usertype;

    public BankUser(String username, String password, UserType usertype) {
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public boolean auth(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return username + " - " + usertype;
    }

    public static String toJson(List<BankUser> bankUsers){
        Gson gson = new Gson();

        TypeToken<List<BankUser>> typeToken = new TypeToken<>(){};
        return gson.toJson(bankUsers, typeToken.getType());
    }

    public static List<BankUser> listFrom(String json){
        final List<BankUser> bankUsers = new ArrayList<>();

        for (JsonElement jsonElement : JsonParser.parseString(json).getAsJsonArray()){
            bankUsers.add(BankUser.from(jsonElement.toString()));
        }
        return bankUsers;
    }

    public static BankUser from(String json){
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        String usertype = jsonObject.get("usertype").getAsString();
        if (usertype.equals(CLIENT))
            return gson.fromJson(json, Client.class);
        else if (usertype.equals(MANAGER))
            return gson.fromJson(json, Manager.class);

        return null;
    }
}
