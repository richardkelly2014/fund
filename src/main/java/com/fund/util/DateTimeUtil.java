package com.fund.util;

import lombok.experimental.UtilityClass;
import org.mozilla.universalchardet.UniversalDetector;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@UtilityClass
public final class DateTimeUtil {

    private final static DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static int getWeekByDay(String day) {
        LocalDate date = LocalDate.parse(day, dayFormatter);
        return date.getDayOfWeek().getValue();
    }

    public static String addDay(String day, int plus) {
        LocalDate localDate = LocalDate.parse(day, dayFormatter);
        return localDate.plusDays(plus).format(dayFormatter);
    }

    public static int differDay(String day) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.parse(day, dayFormatter);

        return (int) (now.toEpochDay() - date.toEpochDay());
    }

    public static String getEncoding(byte[] bytes) {
        UniversalDetector detector =new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        return encoding;
    }
}
