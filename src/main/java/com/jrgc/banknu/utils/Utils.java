package com.jrgc.banknu.utils;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.models.BankUser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {
    public static String getGreeting(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 17 || hour < 4)
            return "Boa noite";
        if (hour > 11)
            return "Boa tarde";

        return "Bom dia";
    }

    public static String getCurrentDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(new Date());
    }
}
