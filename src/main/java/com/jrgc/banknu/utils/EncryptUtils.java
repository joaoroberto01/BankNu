package com.jrgc.banknu.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtils {
    public static String toSHA1(String s){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] encBytes = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, encBytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String base64Decode(String encoded){
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static String base64Encode(String string){
        byte[] encoded = Base64.getEncoder().encode(string.getBytes(StandardCharsets.UTF_8));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
