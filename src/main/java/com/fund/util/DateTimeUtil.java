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
}
