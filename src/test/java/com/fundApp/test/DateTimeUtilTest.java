package com.fundApp.test;

import cn.hutool.core.util.NumberUtil;
import com.fund.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class DateTimeUtilTest {


    @Test
    public void test01() {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        Date date = Date.from(instant);

        Date dayOfStar = getDayOfStart(date);
        int part = 0;
        int startPart = part - 2;

        int startMin = startPart * 5;
        int endMin = part * 5 + 5;

        Date start = plusMinutes(dayOfStar, startMin);
        Date end = plusMinutes(dayOfStar, endMin);

        log.info("{},{}", dateFormat(start, ""), dateFormat(end, ""));
    }

    String dateFormat(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 增加分钟
     *
     * @param date
     * @param min
     * @return
     */
    Date plusMinutes(Date date, int min) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone).plusMinutes(min);
        return Date.from(localDateTime.atZone(zone).toInstant());
    }

    /**
     * 获取一天的开始
     *
     * @param date
     * @return
     */
    Date getDayOfStart(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();

        LocalDateTime start = LocalDateTime.ofInstant(instant, zoneId).withHour(0).withMinute(0).withSecond(0);

        return Date.from(start.atZone(zoneId).toInstant());
    }

    @Test
    public void test1() {

        log.info("{}", DateTimeUtil.getWeekByDay("2020-06-28"));
    }

    @Test
    public void test4() {
        log.info("{}", DateTimeUtil.getYearMonthDayWeek("20200101", "yyyyMMdd"));
        log.info("{}", DateTimeUtil.getYearMonthDayWeek("20191230", "yyyyMMdd"));
        log.info("{}", DateTimeUtil.getYearMonthDayWeek("20200930", "yyyyMMdd"));
        log.info("{}", DateTimeUtil.getYearMonthDayWeek("20201001", "yyyyMMdd"));
    }

    @Test
    public void test5() {
        log.info("{}", DateTimeUtil.minusDay("20200101", "yyyyMMdd", 1));

        log.info("{}", DateTimeUtil.plusDay("20200101", "yyyyMMdd", 1));
    }

    @Test
    public void test6() {
        String tradeDate = "20200824";
        String td = "20200823";
        log.info("{}", td.compareTo(tradeDate));
    }

    @Test
    public void test7() {
        float preClose = 5.55f;//12.25f;
        float tmpVal = NumberUtil.round(NumberUtil.mul(preClose, 0.1), 2).floatValue();

        log.info("{}", tmpVal);
        float up = (float) NumberUtil.add(preClose, tmpVal);
        float down = (float) NumberUtil.sub(preClose, tmpVal);

        log.info("{},{},{}", preClose, up, down);
    }

    @Test
    public void test2() {
        String value = "jsonpgz({});";
        log.info("{}", value.substring(8, value.length() - 2));
    }

    @Test
    public void test3() {
        System.out.println("Hello,Akina!");
        System.out.println("\033[30;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[31;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[32;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[33;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[34;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[35;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[36;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[37;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[40;31;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[41;32;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[42;33;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[43;34;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[44;35;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[45;36;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[46;37;4m" + "Hello,Akina!" + "\033[0m");
        System.out.println("\033[47;4m" + "Hello,Akina!" + "\033[0m");
    }
}
