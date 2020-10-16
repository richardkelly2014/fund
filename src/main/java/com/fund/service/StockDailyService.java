package com.fund.service;


import com.fund.model.StockDailyModel;

import java.util.List;

public interface StockDailyService {

    int insert(String tsCode,
               String symbol,
               String tradeDate,
               String open,
               String high,
               String low,
               String close,
               String preClose,
               String change,
               String pctChg,
               String vol,
               String amount,
               int year,
               int month,
               int day,
               int week);

    int batchInsert(List<List<Object>> dailys);

    /**
     * 修改日期
     *
     * @param id
     * @param year
     * @param month
     * @param day
     * @param week
     * @return
     */
    int changeDate(Integer id, Integer year,
                   Integer month, Integer day,
                   Integer week);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<StockDailyModel> loadAllDailyData();

    /**
     * 根据tsCode 查询
     *
     * @param tsCode
     * @param sort
     * @return
     */
    List<StockDailyModel> loadDailyByCode(final String tsCode, boolean sort);

    /**
     * 查询交易日范围
     *
     * @param tsCode
     * @param start
     * @param end
     * @param sort
     * @return
     */
    List<StockDailyModel> loadDailyByCode(final String tsCode,
                                          final String start,
                                          final String end, boolean sort);

    /**
     * 查询 交易日最近一条记录
     *
     * @param tsCode
     * @param tradeDate
     * @return
     */
    StockDailyModel loadDailyLastByTradeDate(final String tsCode, final String tradeDate);
}
