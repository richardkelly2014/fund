package com.fund.service.impl;

import com.fund.client.StockClient;
import com.fund.service.StockDailyBusinessService;
import com.fund.service.StockDailyService;
import com.fund.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDailyBusinessServiceImpl implements StockDailyBusinessService {

    @Autowired
    private StockClient stockClient;
    @Autowired
    private StockDailyService stockDailyService;

    @Override
    public void syncOneDate(String tsCode, String tradeDate) {

        List<List<String>> items = stockClient.loadStockByDay(tsCode, tradeDate, null, null);
        if (items != null && items.size() > 0) {
            process(items);
        }
    }

    @Override
    public void syncDate(String tsCode, String startDate, String endDate) {
        List<List<String>> items = stockClient.loadStockByDay(tsCode, null, startDate, endDate);
        if (items != null && items.size() > 0) {
            process(items);
        }
    }

    /**
     * 分析
     *
     * @param tsCode
     * @param startDate 开始时间
     * @param endDate
     */
    @Override
    public void analysis(String tsCode, String startDate, String endDate) {

    }

    private void process(List<List<String>> items) {
        items.stream().forEach(item -> {

            String tsCode = item.get(0);
            String symbol = StringUtils.split(tsCode, ".")[0];
            String tradeDate = item.get(1);
            String open = item.get(2);
            String high = item.get(3);
            String low = item.get(4);
            String close = item.get(5);
            String preClose = item.get(6);
            String change = item.get(7);
            String pctChange = item.get(8);

            String vol = item.get(9);
            String amount = item.get(10);

            int[] result = DateTimeUtil.getYearMonthDayWeek(tradeDate, "yyyyMMdd");
            int year = result[0];
            int month = result[1];
            int day = result[2];
            int week = result[3];

            stockDailyService.insert(tsCode, symbol, tradeDate, open, high, low, close, preClose, change, pctChange,
                    vol, amount, year, month, day, week);
        });
    }

}
