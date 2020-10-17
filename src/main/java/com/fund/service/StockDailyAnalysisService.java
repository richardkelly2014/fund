package com.fund.service;


import com.fund.model.StockDailyAnalysisModel;

import java.util.List;

/**
 * 日行情分析
 */
public interface StockDailyAnalysisService {

    /**
     * 创建
     *
     * @param tsCode
     * @param symbol
     * @param tradeDate
     * @param changeType
     * @param changeStop
     * @param changeStopNum
     * @param lowDay
     * @param highDay
     */
    int create(String tsCode,
               String symbol,
               String tradeDate,
               Integer changeType,
               Integer changeStop,
               Integer changeStopNum,
               Integer lowDay,
               Integer highDay,
               Integer changeStopReach,
               String crossStar);

    int create(StockDailyAnalysisModel model);

    /**
     * 批量
     *
     * @param list
     * @return
     */
    int create(List<StockDailyAnalysisModel> list);

    /**
     * 查询某一天交易日
     *
     * @param tsCode
     * @param tradeDate
     * @return
     */
    StockDailyAnalysisModel loadByTradeDate(String tsCode, String tradeDate);

}
