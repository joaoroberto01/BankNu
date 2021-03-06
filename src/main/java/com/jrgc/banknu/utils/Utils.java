package com.jrgc.banknu.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static String getCurrencyFormatted(float balance){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return numberFormat.format(balance);
    }
}
