package org.aleks4ay.copier.tools;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class DateConverter {
    private static final Calendar calendar = Calendar.getInstance();
    private static final Calendar calendarAfterPeriod = Calendar.getInstance();

    public static long getNowDate(){
        return new Date().getTime();
    }

    private static int getYear(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    public static int getYearShort(long millis) {
        String year = String.valueOf(getYear(millis));
        return Integer.parseInt(year.substring(2));
    }

    public static long offset (long millisecond, int duration) {
        calendarAfterPeriod.setTimeInMillis(millisecond);
        calendarAfterPeriod.add(Calendar.DATE, duration);
        return calendarAfterPeriod.getTimeInMillis();
    }

    public static String getStrFromTimestamp(Timestamp date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        LocalDateTime localDateTime = date.toLocalDateTime();
        return localDateTime.format(formatter);
    }

    public static String dateToString(long millis) {
        if (millis == 0) {
            return "-";
        }
        String date = "";
        calendar.setTimeInMillis(millis);
        int day, month;
        if ((day = calendar.get(Calendar.DAY_OF_MONTH))<10) {
            date += "0" + day + ".";
        }
        else {
            date += "" + day + ".";
        }
        if ((month = calendar.get(Calendar.MONTH) + 1 )<10) {
            date += "0" + month;
        }
        else {
            date += month;
        }
        return date + "." + calendar.get(Calendar.YEAR);
    }

    static String timeToString(long millis) {
        calendar.setTimeInMillis(millis);
        String time = "";
        int hour, min;
        if ((hour = calendar.get(Calendar.HOUR_OF_DAY))<10) {
            time += "0" + hour + ":";
        }
        else {
            time += hour + ":";
        }
        if ((min = calendar.get(Calendar.MINUTE) )<10) {
            time += "0" + min;
        }
        else {
            time += min;
        }
        return time;
    }
}
