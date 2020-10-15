package com.fundApp.test;

import cn.hutool.core.util.NumberUtil;
import com.fund.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DateTimeUtilTest {

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
        String tradeDate = "20200928";
        String td = "20200925";
        log.info("{}", tradeDate.compareToIgnoreCase(td));
    }

    @Test
    public void test7() {
        float preClose = 7.6f;//12.25f;


        float up = NumberUtil.round(NumberUtil.mul(preClose, (1 + 0.1)), 2).floatValue();
        float down = NumberUtil.round(NumberUtil.mul(preClose, (1 - 0.1)), 2).floatValue();

        log.info("{}", (up - up)==0);

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
