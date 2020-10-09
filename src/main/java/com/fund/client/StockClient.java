package com.fund.client;

import java.util.List;

public interface StockClient {

    /**
     * 加载stock
     */
    List<List<String>> loadAllStock();

    /**
     * 获取 tsCode 日线行情
     * @param tsCode
     * @param tradeDate
     * @param startDate
     * @param endDate
     */
    List<List<String>> loadStockByDay(String tsCode,
                        String tradeDate,
                        String startDate,
                        String endDate);
}
