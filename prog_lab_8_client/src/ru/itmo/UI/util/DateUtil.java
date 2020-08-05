package ru.itmo.UI.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {



    private DateUtil() {};


    public static String dateToStringCanonical(Date date) {

        return dateToString(date, "yyyy-mm-dd hh:mm:ss");
    }


    public static String dateToString(Date date, String dateFormat) {

        return new SimpleDateFormat(dateFormat).format(date);

    }
}
