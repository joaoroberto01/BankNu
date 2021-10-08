package com.jrgc.banknu.utils;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.BankUser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class UserPersist {
    private static final String AUTH_FILE = "auth.bn";

    public static void fetchUsers(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(AUTH_FILE));

            String line, read = "";
            while ((line = bufferedReader.readLine()) != null)
                read = read.concat(line);

            bufferedReader.close();

//            String json = EncryptUtils.base64Decode(read);
            String json = read;
            List<BankUser> bankUsers = BankUser.listFrom(json);

            BankApplication.bankUsers.addAll(bankUsers);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void storeUsers(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(AUTH_FILE));

            String j = BankUser.toJson(BankApplication.bankUsers);
            System.out.println(j);
            //String json = EncryptUtils.base64Encode(j);
            String json = j;

            bufferedWriter.write(json);
            bufferedWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
