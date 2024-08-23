package com.sos.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static String formatLongTimestampToDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:hh:mm");
        return dateFormat.format(date);
    }
}
