package com.adplatform.restApi.global.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtils {

    //파라미터의 이전달 년월일 전달을 구한다.
    public static String getBeforeYearMonthByYMD(String yearMonth, int minVal){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(yearMonth.substring(0,4));
        int month = Integer.parseInt(yearMonth.substring(4,6));
        int day = Integer.parseInt(yearMonth.substring(6,8));

        cal.set(year, month-minVal-1, day);

        String beforeYear = dateFormat.format(cal.getTime()).substring(0,4);
        String beforeMonth = dateFormat.format(cal.getTime()).substring(4,6);
        String beforeDay = dateFormat.format(cal.getTime()).substring(6,8);
        String retStr = beforeYear + beforeMonth + beforeDay;

        System.out.println("retStr : "  + retStr);
        return retStr;
    }

    //파라미터의 전일 년월일 전달을 구한다.
    public static String getBeforeYearMonthDayByYMD(String yearMonth, int minVal){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(yearMonth.substring(0,4));
        int month = Integer.parseInt(yearMonth.substring(4,6));
        int day = Integer.parseInt(yearMonth.substring(6,8));

        cal.set(year, month-1, day-minVal);

        String beforeYear = dateFormat.format(cal.getTime()).substring(0,4);
        String beforeMonth = dateFormat.format(cal.getTime()).substring(4,6);
        String beforeDay = dateFormat.format(cal.getTime()).substring(6,8);
        String retStr = beforeYear + beforeMonth + beforeDay;

        System.out.println("retStr : "  + retStr);
        return retStr;
    }

    //해당년월의 마지막 날짜를 구한다.
    public static String getLastDayOfMonthByYMD(String yearMonth){
        String year = yearMonth.substring(0,4);
        String month = yearMonth.substring(4,6);

        int _year = Integer.parseInt(year);
        int _month = Integer.parseInt(month);

        Calendar calendar = Calendar.getInstance();
        calendar.set(_year, (_month-1), 1); //월은 0부터 시작
        String lastDay = String.valueOf(calendar.getActualMaximum(Calendar.DATE));
        String retStr = year + month + lastDay;

        System.out.println("retStr  : " + retStr);

        return retStr;
    }
}
