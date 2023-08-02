package com.example.fin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat sdfAmer = new SimpleDateFormat("yyyy.MM.dd");
    public static Calendar stringToDate(String strDate) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(sdf.parse(strDate));
        return date;
    }

    public static Calendar getLaterDate(Calendar date1, Calendar date2) {
        return date1.after(date2) ? date1 : date2;
    }

    /**
     * Convert date to string (dd.MM.yyyy)
     * @param date date to convert
     * @return string date
     */
    public static String dateToString(Calendar date) {
        return sdf.format(date.getTime());
    }

    /**
     * Convert date to string in American format (yyyy.MM.dd)
     * @param date date to convert
     * @return string date in American format
     */
    public static String dateToStringAmer(Calendar date) {
        return sdfAmer.format(date.getTime());
    }
}
