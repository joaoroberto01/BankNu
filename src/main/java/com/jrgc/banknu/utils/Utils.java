package com.jrgc.banknu.utils;

import java.util.Calendar;

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
}