package com.fund.service;

public interface StockDailyBusinessService {

    /**
     * 同步某一天
     *
     * @param tsCode
     * @param tradeDate
     */
    void syncOneDate(String tsCode, String tradeDate);

    /**
     * 同步一段时间
     *
     * @param tsCode
     * @param startDate
     * @param endDate
     */
    void syncDate(String tsCode, String startDate, String endDate);

    /**
     * 第一次分析
     *
     * @param tsCode
     */
    void analysis(String tsCode, String startDate, String endDate);


}
