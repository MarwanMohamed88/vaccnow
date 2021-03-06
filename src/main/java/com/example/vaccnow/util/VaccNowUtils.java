package com.example.vaccnow.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class VaccNowUtils {

    private VaccNowUtils() {
    }

    public static Date getDate(Long dateLong) {
        if (dateLong == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTimeInMillis(dateLong);
        return c.getTime();
    }

    public static Long getTimeInMillis(Date date) {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static String getRandomScheduledVaccinationRequest() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 15;

        return new Random().ints(leftLimit, rightLimit + 1).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    }

    public static Date addMinuteToDate(Date date, Integer minutes) {
        if (date == null || minutes == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addHoursToDate(Date date, Integer hours) {
        if (date == null || hours == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date setHoursToDate(Date date, Integer hours) {
        if (date == null || hours == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

}
