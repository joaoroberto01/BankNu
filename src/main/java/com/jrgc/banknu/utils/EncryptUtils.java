package com.jrgc.banknu.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
    public static String toSHA1(String s){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] encBytes = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, encBytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
