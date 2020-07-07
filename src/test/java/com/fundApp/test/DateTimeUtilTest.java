package com.fundApp.test;

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
    public void test2() {
        String value = "jsonpgz({});";
        log.info("{}", value.substring(8, value.length() - 2));
    }
}
