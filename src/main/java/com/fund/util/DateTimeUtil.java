package com.fund.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public final class DateTimeUtil {

    private final static DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static int getWeekByDay(String day) {
        LocalDate date = LocalDate.parse(day, dayFormatter);
        return date.getDayOfWeek().getValue();
    }

    public static int[] getYearMonthDayWeek(String day, String formatter) {
        int[] result = new int[4];
        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ofPattern(formatter));

        int year = date.getYear();
        int month = date.getMonth().getValue();
        int intDay = date.getDayOfMonth();
        int week = date.getDayOfWeek().getValue();

        result[0] = year;
        result[1] = month;

        result[2] = intDay;
        result[3] = week;

        return result;
    }

    public static String addDay(String day, int plus) {
        LocalDate localDate = LocalDate.parse(day, dayFormatter);
        return localDate.plusDays(plus).format(dayFormatter);
    }

    /**
     * 增加天数
     *
     * @param day
     * @param formatter
     * @param plus
     * @return
     */
    public static String plusDay(String day, String formatter, int plus) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        LocalDate localDate = LocalDate.parse(day, dateTimeFormatter);
        return localDate.plusDays(plus).format(dateTimeFormatter);
    }

    /**
     * 减少天数
     *
     * @param day
     * @param formatter
     * @param plus
     * @return
     */
    public static String minusDay(String day, String formatter, int plus) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        LocalDate localDate = LocalDate.parse(day, dateTimeFormatter);
        return localDate.minusDays(plus).format(dateTimeFormatter);
    }

    public static int differDay(String day) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.parse(day, dayFormatter);

        return (int) (now.toEpochDay() - date.toEpochDay());
    }


}
