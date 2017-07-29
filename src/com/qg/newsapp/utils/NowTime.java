package com.qg.newsapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NowTime {
    public static String CurrentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String dateStr = formatter.format(cal.getTime());
        return  dateStr;
    }
}
