package com.jrgc.banknu.models;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BankUser {
    public enum UserType {
        @SerializedName("0")
        CLIENT,
        @SerializedName("1")
        MANAGER;

        @Override
        public String toString() {
            return switch (this){
                case CLIENT -> "Cliente";
                case MANAGER -> "Gerente";
            };
        }
    }

    private String username, password;
    private UserType usertype;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public boolean auth(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
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

        return jsonObject.get("usertype").getAsInt() == 0 ?
                gson.fromJson(json, Client.class) : gson.fromJson(json, Manager.class);
    }
}
