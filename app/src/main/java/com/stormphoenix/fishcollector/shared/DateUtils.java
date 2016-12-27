package com.stormphoenix.fishcollector.shared;

import java.util.Calendar;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DateUtils {
    public static String getDay() {
        Calendar instance = Calendar.getInstance();
        int day = instance.get(Calendar.DAY_OF_MONTH);
        if (day >= 1 && day < 10) {
            return "0" + String.valueOf(day);
        } else {
            return String.valueOf(day);
        }
    }

    public static String getHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 10) {
            return "0" + String.valueOf(hour);
        } else {
            return String.valueOf(hour);
        }
    }

    public static String getMinute() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        if (minute >= 0 && minute < 10) {
            return "0" + String.valueOf(minute);
        } else {
            return String.valueOf(minute);
        }
    }

    public static String getSec() {
        Calendar calendar = Calendar.getInstance();
        int sec = calendar.get(Calendar.SECOND);
        if (sec >= 0 && sec < 10) {
            return "0" + String.valueOf(sec);
        } else {
            return String.valueOf(sec);
        }
    }

    public static String getMonNum() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.MONTH) + 1;
        if (i >= 1 && i < 10) {
            return "0" + String.valueOf(i);
        } else {
            return String.valueOf(i);
        }
    }

    public static String getMonStr() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.MONTH) + 1;
        String mon = null;
        switch (i) {
            case 1:
                mon = "JAN";
                break;
            case 2:
                mon = "FEB";
                break;
            case 3:
                mon = "MAR";
                break;
            case 4:
                mon = "APR";
                break;
            case 5:
                mon = "MAY";
                break;
            case 6:
                mon = "JUN";
                break;
            case 7:
                mon = "JUL";
                break;
            case 8:
                mon = "AUG";
                break;
            case 9:
                mon = "SEP";
                break;
            case 10:
                mon = "OCT";
                break;
            case 11:
                mon = "NOV";
                break;
            case 12:
                mon = "DEC";
                break;
            default:
                break;
        }
        return mon;
    }

    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }


    public static String mergeTime(int year, int monthOfYear, int dayOfMonth) {
        return String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
    }
}
